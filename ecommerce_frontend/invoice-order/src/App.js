import React, {useState} from "react";
import LoginForm from "./components/LoginForm"

function App() {

  const [user, setUser] = useState({customerName:"",customerSurname:"",userName:"", email:"", password:""});
  const [error, setError] = useState("");

  const Login = details =>{
    setUser({name:details.name, email: details.email});
    console.log(details);
  }
  
  const Logout = () =>{
    setUser({customerName:"",customerSurname:"",userName:"", name:"",email:""});
  }
  
  return (
    <div className="App">
      {(user.password != "") ? (
      // The button does logout operation
        <div className = "welcome-inner">
            <h2>Congrats! You successfully updated your account information.</h2>
            <button onClick={Logout}>Back to Home</button> 
        </div>

      ): (
          <LoginForm Login = {Login} error = {error}/>
      )}
    </div>
  );
}

export default App;