import '../App.css';
import Loader from 'react-loader-spinner';
import React, {useState} from 'react';
import { Button } from '@material-ui/core';
import SearchAppBar from "./Navbar"
import {NavSidebar} from "./NavSideBar"
import ProductCard from "./ProductCard"
import SearchBar from "material-ui-search-bar";




const PAGE_PRODUCT = "products";
const PAGE_CART = "cart";

export class Campaigns extends React.Component {
    state = {
        products:[],
        quantity: 1,
        search: "",

    }
    componentDidMount(){
        fetch('/sales_manager/campaign' , {
            method: 'GET',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
          }).then(response => {return response.json() }
          ).then(products => {
              console.log(products)
              this.setState(()=>{
                  return{
                      products:products
                  }
              })
          });
    }

    Search()
    {
      var obj = new Object()
      obj.query = this.state.search
      var jsonobj = JSON.stringify(obj)

      fetch('/products/search_campaign', {
      method: 'POST',
      mode: 'cors',
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
        body: jsonobj,
      }).then(response => {return response.json() }
      ).then(products => {
        console.log(products)
        this.setState(()=>{
            return{
                products:products
            }
        })
      });
    }

    handleClick(pid,quantity1,productname) {

      var obj = new Object()
      obj.userId = sessionStorage.getItem("userid")
      obj.quantity = quantity1
      var jsonobj = JSON.stringify(obj)

      fetch('/cart/' + pid , {
      method: 'POST',
      mode: 'cors',
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: jsonobj,
    }).then(response => {return response.json() }
    ).then(user => {
        if(user.message){
          
            alert(user.message)
        }
        else{
          alert(quantity1 + " " + productname +" is successfully added to your cart.")
        }
    });
  }
 
nextPath(path) {
  this.props.history.push(path);
}

render() {
    
    return(
      <body>
        <> 
        <div>
        <SearchBar className="searchbar"
          value={this.state.search}
          onChange={(newValue) => this.setState({ search: newValue })}
          onRequestSearch={() => this.Search()}
          placeholder="Search by name or description"
        />
        </div>
        <div className="products"> 
          {this.state.products.map((product, idx ) =>(
            <div>
            <ProductCard product={product}></ProductCard>
            </div>
          ))}
        </div>
      </>
    </body>)

};
}
export default Campaigns;
