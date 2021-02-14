package edu.sabanciuniv.cs308.ecommerce.configs;

import java.util.HashMap;

public class ShoppingConfig {
    public static Boolean validationwithHashMap(String keys[],HashMap<String,String> request) throws Exception{
        Boolean status = false;
        try{
            for (int start = 0; start < keys.length ;start++){
                if(request.containsKey(keys[start])){ //not exist
                    if(request.get(keys[start]).equals("")){ //if empty
                        throw new Exception(keys[start] + " Should not be empty") ;
                    }

                }
                else{
                    throw new Exception(keys[start] + " is missing") ;
                }

            }
        }catch(Exception e){
            throw new Exception("Error is " + e.getMessage());
        }
        return status;
    }
}
