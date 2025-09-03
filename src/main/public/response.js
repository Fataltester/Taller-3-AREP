function loadGetMsg() {
  let nameVar = document.getElementById("name").value;
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function() {
    document.getElementById("getrespmsg").innerHTML = this.responseText;
  }
  xhttp.open("GET", "/hello?name=" + nameVar);
  xhttp.send();
}

