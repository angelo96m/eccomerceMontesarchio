
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
};

function isLogged()
{
	
	var resultbool = false;
	$.ajax({
        url: "/ecommerceMontesarchio/servlet/isLogged",
        type: 'get',
        async: false,
        success: function(data) {
            result = data;
    		
            //alert(result);

            if(data != "Not Logged")
    		{
    			var obj = JSON.parse(data);
    			//$("#WelcomeMessage").text("Benvenuto " + obj.Nome + " " + obj.Cognome);
    			//$("#WelcomeMessage").append("<a href='index.html' class='text-uppercase' onclick='Logout();'> Exit</a>");
    			//$('#my-signin2').hide();
    			//showMenuForUser();
    			resultbool = true;
    		}
    		else
    		{		
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
		
	
	//$('#return-msg').addClass('show-return-msg');
	/*
	$('.return-msg').on('click', function(e) {
		$(this).removeClass('show-return-msg');
	}); 
	*/
	if(Nome != "" && Cognome != ""  && Password != "" && ConfermaPassword != "" && Mail != "" && Via != "" && Nickname != "" && CodiceFiscale !="")
	{
		var indirizzo = Via;
		
		/*
		alert(Nome); 
		alert(Cognome); 
		alert(Password); 
		alert(ConfermaPassword); 
		alert(Mail); 
		alert(Via); 
		alert(Nickname); 
		alert(CodiceFiscale); 
		*/
		
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
	
	signOut();
	$.get("/Ecommerce/servlet/Logout", function(data) {
		$('#my-signin2').hide();
		location.reload();
	});
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('Utente disconnesso.');
    });
}

function showMenuForUser(){
	$('#loadInfoLocal').hide();
	//come mettere?
	$('#RegistrazionePanel').hide();
	$('#MioProfiloPanel').show();
	
	if ($('#iconLogin').hasClass('ti-user')){
		$('#iconLogin').removeClass('ti-user'); 
		$('#iconLogin').addClass('ti-shift-right'); 
	}
	
}

function hideMenuForUser(){
	$('#loadInfoLocal').hide();
	//come mettere?
	$('#RegistrazionePanel').show();
	$('#MioProfiloPanel').hide();

	
	
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
	                "<span class='mr-4'> " + array[j].Prezzo +  "</span>" +
	                "<del>  "+ (array[j].Prezzo+15) + " </del>" +
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
for(var i=0; i<array.length; i++){
    		cont = cont + array[i].Prezzo; 
    		context = context + "<tr>" +  
           "<td>" +
              "<div class='media'>" + 
                "<div class='d-flex'>" + 
                  "<img" +
                    "src='img/product/single-product/cart-1.jpg'" + 
                    "<p hidden id='idProdotto"+i+"'>"+array[i].idProdotto+"</p> " +
                    "<img class='img-fluid w-100' src='" + array[i].ImgURL +
                    "alt=''" +
                 " />" +
                "</div>" +
                "<div class='media-body'>"+
                 // "<p>Minimalistic shop for multipurpose use</p>" +
                //"<a href='#' onclick='addCart(" + i + ") '>" +
                "</div>"+
              "</div>" +
            "</td>" + 
            "<td>" +
              "<h5>" + array[i].Prezzo + "</h5>" +
            "</td>" +
            "<td>" + 
              "<div class='product_count'>" +
                "<input" +
                  "type='text'" +
                  "name='qty'" +
                  "id='sst'" +
                  "maxlength='12'" + 
                  "value='1'" +
                  "title='Quantity:'" + 
                  "class='input-text qty'" +
                "/>" + 
                "<button" +
                  "onclick='var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst )) result.value++;return false;'" +
                  "class='increase items-count'" +
                 "type='button'" +
                ">"+
                  "<i class='lnr lnr-chevron-up'></i>" +
                "</button>" +
                "<button" +
                  "onclick='var result = document.getElementById('sst'); var sst = result.value; if( !isNaN( sst ) &amp;&amp; sst > 0 ) result.value--;return false;'" +
                  "class='reduced items-count'" +
                  "type='button'"+
                ">" +
                  "<i class='lnr lnr-chevron-down'></i>" +
                "</button>" +
              "</div>" +
            "</td>" +
            "<td>" +
              "<h5>" + cont + "</h5>" +
            "</td>" +
          "</tr>"
    		
    		
    		
    	}
        
        	
       
        
   $('#Cart').append(context);
        
    });
}