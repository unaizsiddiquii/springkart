package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.CartDTO;
import com.unaiz.springkart.dto.ProductDTO;
import com.unaiz.springkart.entity.Cart;
import com.unaiz.springkart.entity.CartItem;
import com.unaiz.springkart.entity.Product;
import com.unaiz.springkart.entity.User;
import com.unaiz.springkart.exception.APIException;
import com.unaiz.springkart.exception.ResourceNotFoundException;
import com.unaiz.springkart.repository.CartItemRepository;
import com.unaiz.springkart.repository.CartRepository;
import com.unaiz.springkart.repository.ProductRepository;
import com.unaiz.springkart.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    ProductRepository productRepository;
    CartItemRepository cartItemRepository;
    ModelMapper modelMapper;
    AuthUtil authUtil;

    @Transactional
    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {

        Cart cart = getOrCreateUserCart();

        Product product = getProduct(productId);

        validateStock(product, quantity);
        ensureProductNotAlreadyInCart(cart, productId);

        CartItem cartItem = buildCartItem(cart, product, quantity);
        cartItemRepository.save(cartItem);

        updateCartTotalPrice(cart);

        return mapToCartDTO(cart);
    }


    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if (carts.isEmpty()) {
            throw new APIException("No carts are present.");
        }

        return carts.stream().map(this::mapToCartDTO).toList();
    }

    @Override
    public CartDTO getCart(String email) {

        Cart cart = cartRepository.findCartByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "User Email", email));

        updateCartTotalPrice(cart);

        return mapToCartDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO updateCartProductQuantity(Long productId, Integer quantity, User user) {

        Cart cart = getUserCart(user.getEmail());
        Product product = getProduct(productId);

        CartItem cartItem = getCartItem(cart, productId);

        int updatedQuantity = cartItem.getQuantity() + quantity;

        if (updatedQuantity <= 0) {
            throw new APIException("Product quantity should be at least one.");
        }

        validateStock(product, updatedQuantity);

        cartItem.setQuantity(updatedQuantity);
        cartItemRepository.save(cartItem);

        updateCartTotalPrice(cart);

        return mapToCartDTO(cart);
    }


    @Transactional
    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "ID", cartId));

        if (cart.getCartItems().isEmpty()) {
            throw new APIException("Cart is empty.");
        }

        CartItem cartItem = getCartItem(cart, productId);
        Product product = getProduct(productId);

        cartItemRepository.deleteCartItemByCartIdAndProductId(cartId, productId);

        updateCartTotalPrice(cart);
        cartRepository.save(cart);


        return "Product " + product.getProductName() + ": product id " + product.getProductId() + ". removed from cart successfully.";
    }


//    HELPER METHODS


    private CartDTO mapToCartDTO(Cart cart) {
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();
        List<ProductDTO> productDTOS = cartItems.stream()
                .map((item) -> {
                    ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
                }).toList();

        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    public Cart getOrCreateUserCart() {

        String email = authUtil.loggedInUserEmail();

        return cartRepository.findCartByEmail(email).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(authUtil.loggedInUser());
            cart.setTotalPrice(0.0);
            return cartRepository.save(cart);
        });

    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    private CartItem getCartItem(Cart cart, Long productId) {
        return cartItemRepository.findCartItemByCartIdAndProductId(cart.getCartId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "Product ID", productId));
    }

    private void ensureProductNotAlreadyInCart(Cart cart, Long productId) {
        boolean exists = cartItemRepository
                .findCartItemByCartIdAndProductId(cart.getCartId(), productId)
                .isPresent();

        if (exists) {
            throw new APIException("Product is already in the cart.");
        }
    }

    private void validateStock(Product product, Integer quantity) {
        if (product.getQuantity() <= 0) {
            throw new APIException("Product is out of stock.");
        }
        if (product.getQuantity() < quantity) {
            throw new APIException("Requested quantity is not available. Available quantity: " + product.getQuantity());
        }
    }

    private CartItem buildCartItem(Cart cart, Product product, Integer quantity) {
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .productPrice(product.getSpecialPrice())
                .discount(product.getDiscount())
                .build();
    }

    private void updateCartTotalPrice(Cart cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProductPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }

    private Cart getUserCart(String email) {
        return cartRepository.findCartByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "User Email", email));
    }

}
