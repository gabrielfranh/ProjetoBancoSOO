$("#login-button").on("click", () => {

    var user = document.querySelector("#login-user").value;

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/usuarios/login/" + user,
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Access-Control-Allow-Origin': "*"
        },
        crossDomain: true,
        dataType: "json",
        
    }).then(function(data){
        console.log(data);

        var senha = document.querySelector("#login-password").value;
        
        if(senha == data.senha){
            $(".login-button-text")[0].style = "display: none";
            $(".login-button-img")[0].style = "display: block; margin: 0 auto";

            localStorage.setItem("nome", data.nome);
            localStorage.setItem("id", data.id);


            window.location.href = "../conta.html";
        }else{
            console.log("senha errada")
        }


    });

});

$("#button-signup").on("click", () => {
    
    var nome = document.querySelector("#signup-name");
    var nomeUsuario = document.querySelector("#signup-user");
    var senha = document.querySelector("#signup-password");
    var documento = document.querySelector("#signup-document");

    const json = {
        "nome": nome.value,
        "nomeUsuario": nomeUsuario.value,
        "senha": senha.value,
        "documento": documento.value
    };

    $.ajax({
        method: "POST",
        url: "http://localhost:8080/api/usuarios",
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Access-Control-Allow-Origin': "*"
        },
        crossDomain: true,
        dataType: "json",
        data: JSON.stringify(json)
       
        
    }).then(function(data){
        console.log(data);
    });
});

