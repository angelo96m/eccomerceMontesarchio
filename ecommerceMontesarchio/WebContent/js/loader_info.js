
window.onload = function() {
	 	
	if(isLogged()){
		
		showMenuForUser();	
		loadCartQuantity();
		//alert("isLogged=true");
	}
	else{
		hideMenuForUser();	
	}
	
	
	
	if(window.location.pathname == "/ecommerceMontesarchio/index.html"){
		loadInfoLocal();
	}
	else if(window.location.pathname == "/ecommerceMontesarchio/MioProfilo.html"){
		loadInfoUser();
	}
	else if(window.location.pathname == "/ecommerceMontesarchio/cart.html"){
		if(isLogged()){
			loadCart();
		}
	}
};

function isLogged(){
	
	var resultbool = false;
	$.ajax({
        url: "/ecommerceMontesarchio/servlet/isLogged",
        type: 'get',
        async: false,
        success: function(data) {
            result = data;
    		
            //alert(result);

            if(data != "Not Logged"){
    			var obj = JSON.parse(data);
    			//$("#WelcomeMessage").text("Benvenuto " + obj.Nome + " " + obj.Cognome);
    			//$("#WelcomeMessage").append("<a href='index.html' class='text-uppercase' onclick='Logout();'> Exit</a>");
    			//$('#my-signin2').hide();
    			//showMenuForUser();
    			resultbool = true;
    		}
    		else{		
    			//$('#my-signin2').show();
    			resultbool = false;
    		}
        } 
     });
	return resultbool;
	
}

function registraUser(){
	
	var Nome = $('#Nome').val();
	var Cognome = $('#Cognome').val();
	var Password = $('#Password').val();
	var ConfermaPassword = $('#ConfermaPassword').val();
	var Mail = $('#Mail').val();
	var Via = $('#Via').val();
	var Nickname = $('#Nickname').val();
	var CodiceFiscale = $('#CodiceFiscale').val();
		
	
	if(Nome != "" && Cognome != ""  && Password != "" && ConfermaPassword != "" && Mail != "" && Via != "" && Nickname != "" && CodiceFiscale !="")
	{
		var indirizzo = Via;
		
		
		$.get("/ecommerceMontesarchio/servlet/SalvaUtente?Nome=" + Nome + "&Cognome=" + Cognome + "&Password="+ Password 
				+"&Mail=" + Mail + "&Via=" + Via + "&Nickname=" + Nickname + "&CodiceFiscale=" + CodiceFiscale , function(data) {
			
			alert("Registrato"); 

	    });
	} 
	else if(Nome == "")
	{
		$('#return-msg').text("Inserisci il nome");
	}
	else if(Cognome == "")
	{
		$('#return-msg').text("Inserisci il cognome");
	}
	
	else if(Password == "")
	{
		$('#return-msg').text("Inserisci la password");
	}
	else if(ConfermaPassword == "" || Password != ConfermaPassword)
	{
		if(ConfermaPassword == "")
		{
			$('#return-msg').text("Inserisci di nuovo la password");
		}
		else
		{
			$('#return-msg').text("Le password non coincidono");
		}
	}
	else if(Mail == "")
	{
		$('#return-msg').text("Inserisci la mail");
	}
	else if(Via == "")
	{
		$('#return-msg').text("Inserisci la via");
	}
	
}

function loadCartQuantity(){

	return $.get("/Ecommerce/servlet/GetCart", function(data) {
		
		var array = JSON.parse(data);
		if(array.length > 0){
							
		    	var cartQuantity = 0;
		    	for (var i=0; i<array.length; i++){
		    		var obj = array[i];
		    		//alert(obj.ImageURL);
		    		cartQuantity += obj.Quantity;	
		        }
		    	
		    	$('#cartQuantity').text(cartQuantity);
		}
	});
	
}

function addCart(indexButton){
	
	if(isLogged()){	
		
		var id = $('#idProduct'+indexButton).text();
		 
		$.get("/ecommerceMontesarchio/servlet/AggiungiACarello?idProdotto=" + id, function(data) {
			//alert(data);
			var array = JSON.parse(data);
			
			if(array.length > 0){
				
				//loadCart();
				return true;
		    }
			return false;
		});
	}
	else
		alert("Effettua il login!");
	
}

function Login(){
	var Nickname = $('#Nickname').val();
	var Password = $('#Password').val();
	
	//alert(Nickname);
	//alert(Password);

	if(Nickname != "" && Password != "")
	{
		$.get("/ecommerceMontesarchio/servlet/Login?Nickname=" + Nickname + "&Password=" + Password, function(data) {
			var obj = JSON.parse(data);
			//alert(obj);
			if(obj.Stato == "Logged")
			{
				window.location.href = "index.html";
			}
			else
			{
				alert(obj.Stato);
			}
		
		});
	}
	else
		alert("Inserisci il tuo Nickname e la tua Password");

}

function Logout(){
	
	if ($('#iconLogin').hasClass('ti-shift-right')){
		$('#iconLogin').removeClass('ti-shift-right'); 
		$('#iconLogin').addClass('ti-user'); 

		$.get("/ecommerceMontesarchio/servlet/Logout", function(data) {
			$('#my-signin2').hide();

			location.reload();
		});
	}
}



function showMenuForUser(){
	$('#loadInfoLocal').hide();
	
	$('#RegistrazionePanel').hide();
	$('#MioProfiloPanel').show();
	
	if ($('#iconLogin').hasClass('ti-user')){
		$('#iconLogin').removeClass('ti-user'); 
		$('#iconLogin').addClass('ti-shift-right'); 

	}
	
}

function hideMenuForUser(){
	$('#loadInfoLocal').hide();
	
	$('#RegistrazionePanel').show();
	$('#MioProfiloPanel').hide();

	if ($('#iconLogin').hasClass('ti-shift-right')){
		$('#iconLogin').removeClass('ti-shift-right'); 
		$('#iconLogin').addClass('ti-user'); 

	}
	
	
}

function loadInfoLocal(){
	//mostra i prodotti sullo schermo 3 per riga, in caso crea un altra riga. 
	
	$.get("/ecommerceMontesarchio/servlet/AllProdotti", function(data) {
        var array = JSON.parse(data);
        var row; 
    	

        if ( array.length % 3 >= 1 ) 
        	row = (array.length / 3) +1;
    	else if(array.length < 3 ) 
    		row = 1 ; 
    	else 
    		row = array.length / 3; 
        
        row = Math.trunc(row);
        
        var context = ""; 
        $('#Vetrina').text("");
        
       
        
        j=0;
        while(j<array.length)	
        {
        	
        	for(var i=0; i<=2 && j<array.length; i++)
        	{
        		if(i==0)
        			context = context + "<div class='row'>";
        		/*Context => stringa dove si concatenano gli elementi. 
        		 * j => prende i prodotti 
        		 * i => concatena i prodotti sulla riga. 
        		*/
        		context = context + "<div class='col-lg-4 col-md-6'> <div class='single-product' id='idProdotto"+j+"'>" + 
	            "<div class='product-img'>" + 
	            	"<p hidden id='idProduct"+j+"'>"+array[j].idProdotto+"</p> " + 
	              "<img class='img-fluid w-100' src='" + array[j].ImgURL + "' alt='' />" +
	              "<div class='p_icon'>" +
	                
	                "<a href='#' onclick='addCart(" + j + ") '>" +
	                  "<i class='ti-shopping-cart'></i>" + 
	                "</a>" +
	              "</div>" +
	            "</div>" +
	            "<div class='product-btm'>" +
	              "<a href='#' class='d-block'>" +
	                "<h4> " + array[j].Nome + " </h4>" +
	              "</a>" + 
	              "<div class='mt-3'>" +
	                "<span class='mr-4'> " + array[j].Prezzo +'€' + "</span>" +
	                "<del>  "+ (array[j].Prezzo+15 +'€') + " </del>" +
	                "<div>  "+ (array[j].Descrizione) + " </div>" +
	              "</div>" +
	            "</div>" +
	          "</div></div>";
        		
        		
        		if ( i == 2 || j+1 == array.length){
        			context = context + "</div>"; 
	
        		}
        		j++;
        	}
        }
        	
       
        
   $('#Vetrina').append(context);
        
    });
}

function loadInfoUser()
{
	
}
function loadCart(){
	
	$.get("/ecommerceMontesarchio/servlet/GetCart", function(data) {
        var array = JSON.parse(data);
        var row; 
    	

        
        var context = ""; 
        $('#Cart').text("");
        var cont = 0; 
        
        for(var i=0; i<array.length; i++)
        {
    		//cont = cont + array[i].Prezzo; 
    		context = context + "<tr> " +
						            "<td> " +
						            "<div class='media'> " +
						              "<div class='d-flex'> " +
						                "<img " +
						                  "src= '" + array[i].ImageURL +"' alt='' width='150px' "+
						                "/> "+
						              "</div> "+
						              "<div class='media-body'> "+
						                "<p>"+ array[i].Nome + "</p> "+
						              "</div> "+
						            "</div> "+
						          "</td> "+
						          "<td> "+
						            "<h5>€"+ array[i].Prezzo +"</h5> "+
						          "</td> "+
						          "<td> "+
						            "<div class='product_count'> "+
						              "<input "+
						                "type='text' "+
						                "name='qty' "+
						                "id='sst"+ array[i].idProdotto +"' "+
						                "maxlength='12' "+
						                "value='"+ array[i].Quantità +"' "+
						                "title='Quantity:' "+
						                "class='input-text qty' "+
						              "/> "+
						              "<button "+
						                "onclick='IncreaseQuantityProduct("+ array[i].idProdotto + ")' "+
						                "class='increase items-count' "+
						                "type='button' "+
						              "> "+
						                "<i class='lnr lnr-chevron-up'>+</i> "+
						              "</button> "+
						              "<button "+
						                "onclick= 'DecreaseQuantityProduct("+ array[i].idProdotto +")' "+
						                "class='reduced items-count' "+
						                "type='button' "+
						              "> "+
						                "<i class='lnr lnr-chevron-down'>-</i> "+
						              "</button> "+
						            "</div> "+
						          "</td> "+
						          "<td> "+
						            "<h5>€"+ array[i].Quantità * array[i].Prezzo +"</h5> "+
						          "</td> "+
						        "</tr> ";
    		cont += array[i].Quantità * array[i].Prezzo; 
    		
    		
    	}
        
        context = context + "<tr class='bottom_button'>"+
        
			        "</tr>"+
			        "<tr>"+
			          "<td></td>"+
			          "<td></td>"+
			          "<td>"+
			            "<h5>Subtotale</h5>"+
			          "</td>"+
			          "<td>"+
			            "<h5>€"+ cont +"</h5>"+
			          "</td>"+
			        "</tr>"+
			       
			        "<tr class='out_button_area'>"+
			          "<td></td>"+
			          "<td></td>"+
			          "<td></td>"+
			          "<td>"+
			            "<div class='checkout_btn_inner'>"+
			              
			              "<a class='main_btn' onclick='addOrder()' >Procedi all'Ordine</a>"+
			            "</div>"+
			          "</td>"+
			        "</tr>";
        
        	
       
        
   $('#Cart').append(context);
        
    });
}


function addOrder(){
	//alert("halo");
	return $.get("/ecommerceMontesarchio/servlet/SalvaOrdine", function(data) {
		//alert(data);
		
		if(data == "Ok"){
			alert("Ordine effettuato con successo");
			
		}
		else{
			alert("Il prodotto con nome: " + data + " è esaurito. Massimo 15 prodotti sono disponibili. ");
		}
	});

}

function IncreaseQuantityProduct(id){
	return $.get("/ecommerceMontesarchio/servlet/IncreaseQuantityProduct?idProduct=" + id, function(data) {
		
		loadCart();
			if(data == "Ok"){
				$('#sst'+id).val($('#sst'+id).val + 1 );
				return true;
			}
			else
				return false;
												
	});
}

function DecreaseQuantityProduct(id){
	return $.get("/ecommerceMontesarchio/servlet/DecreaseQuantityProduct?idProduct=" + id, function(data) {
		
		loadCart();
			if(data == "Ok"){
				$('#sst'+id).val($('#sst'+id).val - 1 );
				return true;
			}
			else if(data == "Removed"){
				window.location.replace("/ecommerceMontesarchio/index.html");
			}
			else
				return false;
												
	});
}