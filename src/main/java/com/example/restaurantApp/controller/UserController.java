package com.example.restaurantApp.controller;

import com.example.restaurantApp.model.User;
import com.example.restaurantApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private LoginController loginController=new LoginController();

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/create")
    private ResponseEntity<User> createUser(@RequestBody User user)
   // private String createUser(@RequestBody User user)
    {
        if(!loginController.isLoggedIn() || !loginController.isLoggedInAsAdmin() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String pass=hashPassword(user.getPassword());
        User newUser= new User(
                user.getId_user(),
                user.getName(),
                user.getType(),
                user.getUsername(),
                pass

        );
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        //return pass;
    }




    @PutMapping("/update/{id}")
    private ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user)
    {
        if(!loginController.isLoggedIn())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(!loginController.isLoggedInAsAdmin() && !loginController.getLoggedAs().getId_user().equals(id))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(loginController.isLoggedInAsAdmin() && !loginController.getLoggedAs().getId_user().equals(id))
        {
            User auxUser=userRepository.findById(id).orElse(null);
            if(auxUser!= null)
            {
                if( auxUser.getType().equals("admin"))
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            }
            else { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
        }

        User newUser= userRepository.findById(id).orElse(null);
        if(newUser!=null) {
            newUser.setName(user.getName());
            newUser.setType(user.getType());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            userRepository.save(newUser);

            return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @DeleteMapping("/delete/{id}")
    private String deleteUser(@PathVariable Integer id)
    {
        if(!loginController.isLoggedIn())
            return "Not logged in";

        if(!loginController.isLoggedInAsAdmin() && !loginController.getLoggedAs().getId_user().equals(id))
            return "You can't delete someone else account";

        if(loginController.isLoggedInAsAdmin() && !loginController.getLoggedAs().getId_user().equals(id) && !loginController.getLoggedAs().getUsername().equals("marcofd"))
        {
            User auxUser=userRepository.findById(id).orElse(null);
            if(auxUser!= null)
            {
                if( auxUser.getType().equals("admin"))
                    return "You can'delete other admin's account";

            }
            else { return "User you are trying to delete does not exist"; }
        }
        userRepository.deleteById(id);
        return "deleted";
    }

    @GetMapping("/read/{id}")
    private ResponseEntity<User> readUser(@PathVariable Integer id)
    {
        if(!loginController.isLoggedIn() || !loginController.isLoggedInAsAdmin() )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User newUser=userRepository.findById(id).orElse(null);
        if(newUser!=null)
            return new ResponseEntity<>(newUser,HttpStatus.ACCEPTED);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


    }

    @PutMapping("/login")
    private ResponseEntity<String> tryLogin(@RequestParam(required = false) String username,
                                              @RequestParam(required = false) String password) {
        if(loginController.isLoggedIn())
            return new ResponseEntity<>("Already logged in, please log out", HttpStatus.BAD_REQUEST);

        if (username == null || password == null) {
            return new ResponseEntity<>("Both username and password are required. Or spelled wrong", HttpStatus.BAD_REQUEST);
        }

        User newUser=userRepository.findByUsername(username);

        if(newUser==null)
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);

        String pass;

        if(hashPassword(password)!=null)
        {
            if(hashPassword(password).equals(newUser.getPassword())) {
                loginController.setLoggedIn(true);
                loginController.setLoggedAs(newUser);
                if(newUser.getType().equals("admin"))
                    loginController.setLoggedInAsAdmin(true);
                else
                    loginController.setLoggedInAsAdmin(false);

                System.out.println("Logged in as "+newUser.getName()+" who is "+newUser.getType());
                return new ResponseEntity<>("Logged In succesfully", HttpStatus.ACCEPTED);
            }
            else
                return new ResponseEntity<>("Wrong Password", HttpStatus.CONFLICT);
        }
        System.out.println("Logged in as "+newUser.getName()+"as "+newUser.getType());

         pass=(username+" "+password);
         System.out.println(pass);
        return new ResponseEntity<>(pass,HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/logout")
    private ResponseEntity<String> tryLogout() {
        if(!loginController.isLoggedIn())
            return new ResponseEntity<>("You are not logged in", HttpStatus.BAD_REQUEST);

       loginController.setLoggedAs(null);
       loginController.setLoggedIn(false);
       loginController.setLoggedInAsAdmin(false);
       return new ResponseEntity<>("Logged out succesfully",HttpStatus.OK);
    }


}
