import React, {useState} from 'react';

function LoginForm({Login,error}) {

    const [details, setDetails] = useState({customerName:"", customerSurname: "", userName:"", email:"", password:""});
    
    const submitHandler = e => {
        e.preventDefault();

        Login(details);
    }
    return (
   <form onSubmit = {submitHandler}>
        <div className = "form-inner">
            <h1>Your Invoice</h1>
            <h2></h2>
    {(error != "") ? (<div className= "error">{error}</div>) :""}
            <div className = "form-group">
                <label htmlFor ="receipentName">Receipent Name: Rutkay Yıldırım</label>
                <label htmlFor ="receipentAddress">Receipent Address: Baglarbasi Mahallesi, Yeni Cikmazi, No: 6, Floor:6, Flat: 48</label>
            </div>

            <div className = "form-group">
                <label htmlFor ="itemName">Item Name:    NIKE AIR FORCE ONE</label>
                <label htmlFor ="itemQuantity">Quantity:    1 Price: 100$</label>
            </div>

            <div className = "form-group">
                <label>Item Name:    ADIDAS STAN SMITH GREEN</label>
                <label>Quantity:    2 Price: 250$</label>
            </div>

            <div className = "form-group">
                <label>Total Price: 350$</label>
            </div>

            <div className="submitButton">
                <button type = "submit" value>OK! Great, now take me back</button>   
            </div>

            
        </div>
   </form>
)
}

export default LoginForm;