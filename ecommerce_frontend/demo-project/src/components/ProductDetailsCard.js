import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { Redirect, withRouter,useHistory } from 'react-router-dom';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import ReceiptIcon from '@material-ui/icons/Receipt';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import Rating from '@material-ui/lab/Rating';
import StarsIcon from '@material-ui/icons/Stars';
import Input from '@material-ui/core/Input';



function handleClick(pid,quantity1,productname) {

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

function submitRating(rating,pid) {

    var obj = new Object()
    obj.userId = sessionStorage.getItem("userid")
    obj.rating = rating
    var jsonobj = JSON.stringify(obj)

    fetch('/products/' + pid , {
    method: 'PUT',
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
      if(user.message)
      {
          alert(user.message)
      }
      else{
        alert("You have successfully rated this item.")
        window.location.reload();

      }
  });
}

function commentPost(pid,comment) {

  var obj = new Object()
  obj.userId = sessionStorage.getItem("userid")
  obj.comment = comment
  var jsonobj = JSON.stringify(obj)

  fetch('/products/' + pid + '/comments' , {
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
    if(user.message)
    {
        alert(user.message)
    }
    else{
      alert("You have successfully commented on this item.")
      window.location.reload();

    }
});
}

const useStyles = makeStyles({
  root: {
    width: 1250,
    height: 700,
    border:'1px solid indigo',
  },
  media: {
    height: 250,
    width: 400,
    position: 'relative',
    right: '60%',
    bottom: '160px',
  },
  desc: {
    position: 'relative',
    left: 400,
    bottom: 385,
    fontSize: 30,
    maxWidth: 750,
  },
  title: {
    position: 'relative',
    fontSize: 40,
    right: -70,
    top: 20,
  },
  detailButton: {
    position: 'absolute',
    fontSize: 15,
    left: 875,
    top: 435,
  },
  goback: {
    position: 'absolute',
    fontSize: 15,
    left: 1025,
    top: 435,
  },
  rating: {
    position: 'absolute',
    left: 145,
    top: 430,
  },
  avgRating: {
    position: 'absolute',
    left: 135,
    top: 400,
    fontSize: 17,
  },
  submitRating: {
    position: 'absolute',
    left: 125,
    top: 460,
    fontSize: 15,

  },

  newComment: {
    position: 'absolute',
    left: 125,
    top: 632,
    fontSize: 15,
    width: 1000,
    border: "1px solid black",
    display: "inline",
  },

  newCommentInput: {
    border: "0.2px solid black",
  },


});



export default function ProductCard(product) {
  const classes = useStyles();
  console.log(product.product);
  const history = useHistory();
  const [value, setValue] = React.useState(5);
  const [comment, setComment] = React.useState("");
  var rating = product.product.avgRating;

  const handleChange1 = (event) => {
    setComment(event.target.value);
  };

  console.log(comment)
  return (
    <Card className={classes.root}>

        <CardActionArea>
        <CardActions>
            <Button size="small" onClick={()=>handleClick(product.product.id, 1, product.product.productName)} color="primary" className={classes.detailButton}>
                <ShoppingCartIcon></ShoppingCartIcon>
                    Add To Cart
                </Button>
                <Button size="small" onClick={()=>history.push("/")} color="primary" className={classes.goback}>
                <ArrowBackIcon></ArrowBackIcon>
                    Go Back
                </Button>
            </CardActions>
        <Typography className={classes.title}>
            {product.product.productName}
          </Typography>
        <CardMedia
          component = "img"
          className={classes.media}
          image="https://cdn.tekzen.com.tr/images/product/chilai-home/2791470/chilai-home-enjoy-panda-200x200-cm-cocuk-halisi_500x500_1.jpg"
          title="Contemplative Reptile"
        />
                <Typography className={classes.desc}>
          {product.product.productPrice} TL
          </Typography>
          <Typography className={classes.desc}>
          {product.product.productDescription}
          </Typography>
        <Typography className={classes.avgRating}>
            Average Rating: {rating}
          </Typography>

          <Rating
          name="simple-controlled"
          className={classes.rating}
          value={value}
          onChange={(event, newValue) => {
            setValue(newValue);
          }}
        />
          <Button size="small" onClick={()=>submitRating(value, product.product.id)} color="primary" className={classes.submitRating}>
              <StarsIcon></StarsIcon>
                    Rate Product
                </Button>
        <Typography className= {classes.newComment}>
            <b>New Comment:</b> <Input placeholder="Comment here..." value={comment} onChange={handleChange1}></Input>  <Button onClick={()=>commentPost(product.product.id, comment)} variant="contained" color="primary">Comment</Button>
        </Typography>
      </CardActionArea>     
    </Card>
  );
}
