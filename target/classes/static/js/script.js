console.log("hellooooo")


const toggleSlidebar=()=>{
	if($('.sidebar').is(":visible")){
	//true then band karna hai
	
	$(".sidebar").css("display", "none");
	$(".contentt").css("margin-left","0%");	
	}
	else{
		//false show karna hai
		
		$(".sidebar").css("display", "block");
	$(".contentt").css("margin-left","20%");	
	}
	
	
};


const search=()=>{
//	console.log("searching...")
	
	
	let query=$("#search-input").val();
	
	if(query==''){
		$(".search-result").hide();
	}
	else{
	console.log(query);
	
	//sending request to server-->
	let url=`http://localhost:8080/search/${query}`;
	fetch(url).then((response)=>{
		return response.json();
		
	}).then((data)=>{
		console.log(data);
		let text=`<div class='list-group'>`;
		
		data.forEach((contact) => {
    text += `<a href='/user/contact/${contact.cid}' class='list-group-item list-group-action'> ${contact.cname}</a>`;
});

		
		text+=`</div>`;
		
		$(".search-result").html(text);
		$(".search-result").show();
	});
	
	
		
	}
};




//PAYMENT --------->

const payment=()=>{
	console.log("yes it is working")
	let amount = $("#payment_id").val();
	console.log(amount);
	if(amount== "" || amount==null){
		alert("amount is required!!")
         return;
	}		
	
	
	//code-->we will use ajax to send req to server to create order
	$.ajax(
		{
			url:'/user/create_order',
			data:JSON.stringify({amount:amount,info :'order_request'}),
			contentType:'application/json',
			type:'POST',
			dataType:'json',
			success:function(response){

              console.log(response);
			  if(response.status=="created"){
				//open payment form
  let options={
	key :'rzp_test_nS83NLXh1OyKVS',
	amount : response.amount,
	currency :'INR',
	name: 'Contact Manager',
	description : 'Dontation',
	order_id: response.id,
   handler:function(response){
	console.log(response.razorpay_payment_id);
	console.log(response.razorpay_order_id);
	console.log(razorpay_signature);
  console.log("payment succesfull");
  alert("congrats payment done!!") ;  
},
prefill: { //We recommend using the prefill parameter to auto-fill customer's contact information especially their phone number
	name: "", //your customer's name
	email: "",
	contact: "" //Provide the customer's phone number for better conversion rates 
},
notes: {
	address: "Contact Manager Project"
},
theme: {
	color: "#3399cc"
} 

  };
  let rzp = new Razorpay(options);
  rzp.on('payment.failed', function(response){
	console.log(response.error.code);
	console.log(response.error.description);
	console.log(response.error.source);
	console.log(response.error.step);
	console.log(response.error.reason);
	console.log(response.error.metadata.order_id);
	console.log(response.error.metadata.payment_id);
  })
  rzp.open();



			  }
			},
			error:function(error){
console.log(error)
alert("something went wrong!!")
			}		
		}
	)
	
	
			
}




