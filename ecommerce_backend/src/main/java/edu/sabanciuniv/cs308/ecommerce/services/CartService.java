package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.data_model.UserIdDto;
import edu.sabanciuniv.cs308.ecommerce.entities.*;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.login_system.CartDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.CartRepo;
import edu.sabanciuniv.cs308.ecommerce.repositories.ProductRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepo cartRepository;

    @Autowired
    UserRepository userRepo;

    public CartP showCartDetailsbyUserId(Long cart_id) throws ProductException{
        CartP cart = cartRepository.findCartById(cart_id);
        if(cart == null)
            throw new ProductException("Cart could not be found.");
        else{
            return cart;
        }
    }

    public CartP deleteProductFromCart(CartDto cartdto, Long id) throws ProductException {
        User user = userRepo.findUserById(cartdto.getUserId());
        CartP cart = cartRepository.findCartPByUser_id(user.getId());
        List<CartP_Product> list = user.getCartP_productsList();
        Product product = productRepository.findByid(id);
        Integer index = 0;
        for (CartP_Product cp : list) {
            if (cp.getProduct_name().equals(product.getProductName())) {
                if(cp.getQuantity() == 1) {
                        list.remove(cp);
                        user.setCartP_productsList(list);
                        //cart.setTotal(cart.getTotal() - product.getProductPrice());
                        break;
                }
                else{
                    list.get(index).setQuantity(cp.getQuantity()-1);
                    list.get(index).setPrice(cp.getPrice()- product.getProductPrice());
                    //cart.setTotal(cart.getTotal()- product.getProductPrice());
                    user.setCartP_productsList(list);
                }
            }
            index++;
        }
        userRepo.save(user);
        cartRepository.save(cart);
        return cart;
    }



        //tagtoproduct
    public CartP addProducttoCart(CartDto cartdto, Long id) throws ProductException {

        if (userRepo.findUserById(cartdto.getUserId()) == null) {
            throw new ProductException("User does not exist.");
        } else {
            //Product product = productRepository.findByid(id);
            Product product = productRepository.findByid(id);
            User user = userRepo.findUserById(cartdto.getUserId());
            CartP cart = cartRepository.findCartPByUser_id(user.getId());


            if (productRepository.findByid(product.getId()) == null)
                throw new ProductException("Product does not exist.");
            else if (cart == null) {
                CartP cart2 = new CartP();
                cart2.setId(user.getId());
                cart2.setUser(user);
                //cart2.setTotal((cartdto.getQuantity() * product.getProductPrice()));

                List <CartP_Product> list = user.getCartP_productsList();
                CartP_Product cartP_product= new CartP_Product();
                cartP_product.setPrice(product.getProductPrice().doubleValue() * cartdto.getQuantity());
                cartP_product.setQuantity(cartdto.getQuantity());
                cartP_product.setProduct_name(product.getProductName());
                cartP_product.setProductId(product.getId());
                cartP_product.setImage(product.getProductImage());
                list.add(cartP_product);
                //cart2.setCartP_productsList(list);

                cartRepository.save(cart2);

                return cart2;

            }
            else if (cart != null){

                //List <CartP_Product> lista = user.getCartP_productsList();

                List <CartP_Product> list = user.getCartP_productsList();
                Boolean found = false;

                ListIterator<CartP_Product> it = list.listIterator();

                for (CartP_Product cp : list) {
                    if(cp.getProduct_name().equals(product.getProductName())) {
                        found = true;
                        cp.setPrice(cp.getPrice() + (cartdto.getQuantity().doubleValue() * product.getProductPrice()));
                        cp.setQuantity(cp.getQuantity() + cartdto.getQuantity());
                        cp.setProduct_name(cp.getProduct_name());
                        cp.setProductId(cp.getProductId());
                        cp.setImage(cp.getImage());

                        break;
                    }

                }


                if(found == false) {

                    CartP_Product cartP_product = new CartP_Product();
                    cartP_product.setProduct_name(product.getProductName());
                    cartP_product.setQuantity(cartdto.getQuantity());
                    cartP_product.setPrice((cartdto.getQuantity().doubleValue() * product.getProductPrice()));
                    cartP_product.setProductId(product.getId());
                    cartP_product.setImage(product.getProductImage());
                    list.add(cartP_product);
                }
                cart.setUser(user);
                //cart.setTotal(cart.getTotal() + (cartdto.getQuantity() * product.getProductPrice()));
                cartRepository.save(cart);
                return cart;




            }
            //else if (cart.getProduct_id() == product.getId())
               // throw new ProductException("This product" + cartdto.getProductName() + " already exists.");

            return cart;
        }
    }

    public List<CartP_Product> clearCart(UserIdDto dto) throws ProductException{
        User user = userRepo.findUserById(dto.getUserId());
        if(user == null)
            throw new ProductException("User does not exist.");
        else
        {
            List<CartP_Product> list = new ArrayList<>();
            user.setCartP_productsList(list);
            userRepo.save(user);
            return list;
        }
    }
}
