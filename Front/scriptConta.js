var nomeSession = localStorage.getItem("nome");

document.querySelector("#login-user-label").textContent = "Olá " + nomeSession; 

function getContas(){
    
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/contas?idUsuario=" + localStorage.getItem("id"),
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Access-Control-Allow-Origin': "*"
        },
        crossDomain: true,
        dataType: "json",
        
    })
    .fail(function(jqXHR, textStatus, errorThrown) {
        if (jqXHR.status == 404) {
            document.querySelector("#login-banco-label").style = "display: none";
            document.querySelector("#login-saldo-label").style = "display: none";
        }
    })
    .done(function(data){

        if(localStorage.getItem("conta") == null){
            selectConta(data[0], 1);
        }else{
            conta = parseInt(localStorage.getItem("conta"));

            selectConta(data[conta-1], conta)
        }

        document.querySelector("#login-banco-label").style = "display: block";
        document.querySelector("#login-banco-label").textContent = "Banco " + localStorage.getItem("nomeBanco");

        
        document.querySelector("#login-tipo-label").style = "display: block";
        document.querySelector("#login-tipo-label").textContent = "Tipo " + localStorage.getItem("tipoConta") ;


        document.querySelector("#login-saldo-label").style = "display: block";
        document.querySelector("#login-saldo-label").textContent = "Saldo " + localStorage.getItem("saldo");
    })
}

getContas();


function createConta(){

    var date = Date.now();

    const dataAtualEmMilissegundos = Date.now();
    const dataAtual = new Date(dataAtualEmMilissegundos);
    const ano = dataAtual.getFullYear();
    const mes = (dataAtual.getMonth() + 1).toString().padStart(2, '0');
    const dia = dataAtual.getDate().toString().padStart(2, '0');
    const dataFormatada = `${ano}-${mes}-${dia}`;

    const nomeBanco = document.querySelector("#bank-name").value;
    const tipoConta = document.querySelector("#tipo-conta").value;


    const json = {
        "saldo": 0,
        "idUsuario": localStorage.getItem("id"),
        "dataCriacao": dataFormatada,
        "nomeConta": nomeBanco,
        "tipoConta": tipoConta
    };

    $.ajax({
        method: "POST",
        url: " http://localhost:8080/api/contas",
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

}

$("#button-create-account").on("click", () => {
    createConta();
});


function changeConta(){

    $.ajax({
        method: "GET",
        url: " http://localhost:8080/api/contas?idUsuario=" + localStorage.getItem("id"),
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Access-Control-Allow-Origin': "*"
        },
        crossDomain: true,
        dataType: "json",
        
    }).then(function(data){
        
        var listContas = document.querySelector(".list-contas");

        listContas.innerHTML = "";

        data.forEach(function(obj){

            var idConta = obj.id;
            var saldoConta = obj.saldo;

            var div = document.createElement("a");
            div.href = "#close-modal";
            div.rel = "modal:close";
            div.class  = "close-modal";


            var id = document.createElement("label");
            id.textContent = "Conta " + obj.nomeConta;

            id.style = "margin-right: 10px";

            var saldo = document.createElement("label");
            saldo.textContent = "Saldo " + saldoConta;

            div.append(id);
            div.append(saldo);

            div.style = "display: block; font-size: 20px; color: #fff; margin-bottom: 25px; text-align: center;" ;

            div.addEventListener("click", () => selectConta(obj, idConta));

            listContas.appendChild(div);

        });

    });

}


$("#button-change-account").on("click", () => {
    changeConta();
});


function selectConta(obj, idConta){

    localStorage.setItem("conta", idConta);
    localStorage.setItem("nomeBanco", obj.nomeConta);
    localStorage.setItem("tipoConta", obj.tipoConta);
    localStorage.setItem("saldo", obj.saldo);

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/contas/" + localStorage.getItem("conta") + "?idUsuario=" + localStorage.getItem("id"),
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Access-Control-Allow-Origin': "*"
        },
        crossDomain: true,
        dataType: "json",
        
    })
    .then(function(data){
        console.log(data);

        document.querySelector("#login-banco-label").style = "display: block";
        document.querySelector("#login-banco-label").textContent = "Banco " + obj.nomeConta;

        
        document.querySelector("#login-tipo-label").style = "display: block";
        document.querySelector("#login-tipo-label").textContent = "Tipo " + obj.tipoConta;


        document.querySelector("#login-saldo-label").style = "display: block";
        document.querySelector("#login-saldo-label").textContent = "Saldo " + obj.saldo;

    });

}



$("#button-transfer").on("click",  function(){

    document.querySelector(".transfer-bank-label").textContent = "Banco " + localStorage.getItem("nomeBanco");
    
});


$("#button-transfer-finish").on("click",  function(){

    document.querySelector(".transfer-bank-label").textContent = "Banco " + localStorage.getItem("nomeBanco");

    document.querySelector("#transfer-feeback").textContent = "Transferência Concluida";
    
    if(document.querySelector("#tipo-transferencia").value == "Saque"){


        $.ajax({
            method: "POST",
            url: "http://localhost:8080/api/contas/sacar/" + localStorage.getItem("conta") + "?idUsuario=" + localStorage.getItem("id") + "&valor=" + document.querySelector("#transfer-valor").value,
            headers: {
                'Accept':'application/json',
                'Content-Type':'application/json',
                'Access-Control-Allow-Origin': "*"
            },
            crossDomain: true,
            dataType: "json",
            
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == 400) {
                document.querySelector("#transfer-feeback").textContent = "Saldo insuficiente";
            }
        })
        
    }else if(document.querySelector("#tipo-transferencia").value == "Depósito"){

        $.ajax({
            method: "POST",
            url: "http://localhost:8080/api/contas/depositar/" + localStorage.getItem("conta") + "?idUsuario=" + localStorage.getItem("id") + "&valor=" + document.querySelector("#transfer-valor").value,
            headers: {
                'Accept':'application/json',
                'Content-Type':'application/json',
                'Access-Control-Allow-Origin': "*"
            },
            crossDomain: true,
            dataType: "json",
            
        })

    }

});

$("#button-historio-transfer").on("click",  function(){

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/historico/" + localStorage.getItem("conta"),
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Access-Control-Allow-Origin': "*"
        },
        crossDomain: true,
        dataType: "json",
        
    })
    .then(function(data){
        var listContas = document.querySelector("#list-transfer");

        listContas.innerHTML = "";

        data.forEach(function(obj){

            var div = document.createElement("p");

            var tipo = document.createElement("label");
            tipo.textContent = obj.tipo;

            var valor = document.createElement("label");
            valor.textContent = obj.valorMovimentado;

            var dateTransferencia = document.createElement("label");
            dateTransferencia.textContent = obj.dataMovimento;

            tipo.style = "margin-right: 10px";
            valor.style = "margin-right: 10px";
            dateTransferencia.style = "margin-right: 10px";

            div.textContent = tipo.textContent + " no valor de " + valor.textContent + " reais no dia " + dateTransferencia.textContent;

            listContas.appendChild(div);

            div.style = "display: block; font-size: 20px; color: #fff; margin-bottom: 25px; text-align: center; line-height: 1.3; text-transform: uppercase" ;

        });
    })
});
