const request = new XMLHttpRequest();
const url = "http://localhost:8087/clinic/controller?command=get_dep";
request.responseType =	"json";
request.addEventListener("readystatechange", () => {
    if (request.readyState === 4 && request.status === 200) {
        let obj = request.response;
        for(let val of obj) {
            console.log(val.name);
        }
    }
});

document.getElementById('testVal').addEventListener('click', () => {
    request.open("GET", url, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send();
});

function errorAlert(error,message,button) {
    Notiflix.Report.Failure(error, message+"<br><br>", button);
}