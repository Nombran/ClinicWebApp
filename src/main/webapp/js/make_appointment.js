const allDepRequest = new XMLHttpRequest();
allDepRequest.responseType = "json";
let departmentChoosedId;
let doctorChoosedId;

$(document).ready(() => {
    allDepRequest.addEventListener("readystatechange", () => {
        if (allDepRequest.readyState === 4 && allDepRequest.status === 200) {
            let obj = allDepRequest.response;
            initDepBlocks(obj);
        }
    });
    let url = "http://localhost:8087/clinic/controller?command=get_all_departments";
    allDepRequest.open("GET", url, true);
    allDepRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    allDepRequest.send();
});

const request = new XMLHttpRequest();
request.responseType = "json";
request.addEventListener("readystatechange", () => {
    if (request.readyState === 4 && request.status === 200) {
        let obj = request.response;
        initDocBlocks(obj);
    }
});

function initDepBlocks(allDeps) {
    let count = 0;
    let items = document.createElement('div');
    items.className = 'items';
    let depBlock = document.createElement('div');
    depBlock.className = 'dep-block';
    let depImg = document.createElement('div');
    depImg.className = 'dep-img';
    let depName = document.createElement('h4');
    depName.className = 'dep-name';
    let idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'id';
    depBlock.appendChild(depImg);
    depBlock.appendChild(idInput);
    depBlock.appendChild(depName);
    let active = items.cloneNode(true);
    for (let dep of allDeps) {
        if (count < 4) {
            let stringDepName = dep.name;
            let depId = dep.id;
            let depImgSrc = "../clinic" + dep.imagePath;
            let newDepBlock = depBlock.cloneNode(true);
            newDepBlock.querySelector('.dep-img').style.backgroundImage = "url('" + depImgSrc + "')";
            newDepBlock.querySelector('.dep-name').innerHTML = stringDepName;
            newDepBlock.addEventListener('click', () => {
                $(".dep-choosed").toggle(false);
                let url = "http://localhost:8087/clinic/controller?command=get_doctors_by_department&id="+depId;
                departmentChoosedId = depId;
                request.open("GET", url, true);
                request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                request.send();
                $(".choosed-dep-img")[0].innerHTML = stringDepName;
                $(".choosed-dep-img")[0].style.backgroundImage = "url('" + depImgSrc + "')";
                $(".dep-choosed").toggle(true);
                $('.scroll-container')[0].innerHTML = "";
            });
            active.appendChild(newDepBlock);
            count++;
            if (count == 4) {
                $('.scroll-container')[0].appendChild(active);
                active = items.cloneNode(true);
                count = 0;
            }
        }
    }
    if (count != 0) {
        $('.scroll-container')[0].appendChild(active);
    }
    $('.scroll-container')[0].className = "scroll-container";
    $('.scroll-container').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        adaptiveHeight: false
    });
}

const appRequest = new XMLHttpRequest();
appRequest.responseType = "json";
appRequest.addEventListener("readystatechange", () => {
    if (appRequest.readyState === 4 && appRequest.status === 200) {
        let obj = appRequest.response;
        initFreeBlock(obj);
    }
});

function initDocBlocks(allDocs) {
    let count = 0;
    let docItems = document.createElement('div');
    docItems.className = "doc-items";
    let docItem = document.createElement("div");
    docItem.className = "doc-item";
    let docImg = document.createElement('div');
    docImg.className = 'doc-img';
    let docInfoCon = document.createElement('div');
    docInfoCon.className = 'doc-info-con';
    let surnameBlock = document.createElement('h3');
    surnameBlock.className = 'surname';
    let nameBlock = document.createElement('span');
    nameBlock.className = 'name-lastname';
    let specBlock = document.createElement('span');
    specBlock.className = 'spec';
    let categBlock = document.createElement('span');
    categBlock.className = 'category';
    docInfoCon.appendChild(surnameBlock);
    docInfoCon.appendChild(nameBlock);
    docInfoCon.appendChild(specBlock);
    docInfoCon.appendChild(categBlock);
    docItem.appendChild(docImg);
    docItem.appendChild(docInfoCon);
    let active = docItems.cloneNode(true);
    for (let val of allDocs) {
        if (count < 4) {
            let newDocItem = docItem.cloneNode(true);
            let stringImgPath = "../clinic" + val.imagePath;
            newDocItem.querySelector(".doc-img").style.backgroundImage = "url('" + stringImgPath + "')";
            let name = val.name;
            let surname = val.surname;
            let lastname = val.lastname;
            let spec = val.specialization;
            let category = val.category;
            let id = val.id;
            newDocItem.querySelector(".surname").innerHTML = surname;
            newDocItem.querySelector(".name-lastname").innerHTML = name + " " + lastname;
            newDocItem.querySelector('.spec').innerHTML = spec;
            newDocItem.querySelector('.category').innerHTML = category;
            newDocItem.addEventListener('click', () => {
                $(".doc-choosed").toggle(false);
                let url = "http://localhost:8087/clinic/controller?command=get_free_appointments&id=" + id;
                doctorChoosedId = id;
                appRequest.open("GET", url, true);
                appRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                appRequest.send();
                $(".choosed-doc-img")[0].innerHTML =surname + " " + name;
                $(".choosed-doc-img")[0].style.backgroundImage = "url('" + stringImgPath + "')";
                $(".doc-choosed").toggle(true);
                $('.scroll-container')[0].innerHTML = "";
            });
            active.appendChild(newDocItem);
            count++;
            if (count == 4) {
                $('.scroll-container')[0].appendChild(active);
                active = docItems.cloneNode(true);
                count = 0;
            }
        }
    }
    if (count != 0) {
        $('.scroll-container')[0].appendChild(active);
    }
    $('.scroll-container')[0].className = "scroll-container";
    $('.scroll-container').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        adaptiveHeight: false
    });
}

$(".dep-close")[0].addEventListener('click', ()=> {
    $(".no-doc-apps")[0].style.display = "none";
    $(".doc-choosed").toggle(false);
    $(".dep-choosed").toggle(false);
    $('.scroll-container')[0].innerHTML = "";
    let url = "http://localhost:8087/clinic/controller?command=get_all_departments";
    allDepRequest.open("GET", url, true);
    allDepRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    allDepRequest.send();
});


$(".doc-close")[0].addEventListener('click', () => {
    $(".no-doc-apps")[0].style.display = "none";
    $(".doc-choosed").toggle(false);
    $('.scroll-container')[0].innerHTML = "";
    let url = "http://localhost:8087/clinic/controller?command=get_doctors_by_department&id="+departmentChoosedId;
    request.open("GET", url, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send();
});



function initFreeBlock(freeApps) {
    let prevDateTime;
    let ticket = document.createElement('div');
    ticket.className = "free-appointment";
    let container = document.createElement('div');
    container.className = 'free-appointment-container';
    let slide = document.createElement('div');
    slide.className = 'cur-slide';
    let dateCon = document.createElement('h1');
    dateCon.className = 'date';
    slide.appendChild(dateCon);
    slide.appendChild(container);
    let formCon = document.createElement('form');
    formCon.action = "controller";
    formCon.method = "post";
    let command = document.createElement('input');
    command.type = 'hidden';
    command.name = 'command';
    command.value = 'make_appointment';
    formCon.appendChild(command);
    let submitInput = document.createElement('input');
    submitInput.type = 'button';
    submitInput.value = makeAppointmentButton;
    submitInput.className = "button-class";
    formCon.appendChild(submitInput);
    let active;
    if(freeApps.length === 0 ){
        $(".no-doc-apps")[0].style.display = "flex";
    }
    for(let app of freeApps) {
        let dateTime = app.dateTime;
        let stringDate = dateTime.date.year+"-"+dateTime.date.month+"-"+dateTime.date.day;
        if(prevDateTime == stringDate) {
            let time = dateTime.time.hour+":"+dateTime.time.minute;
            let newTicket = ticket.cloneNode(true);
            newTicket.innerHTML = time;
            let form = formCon.cloneNode(true);
            form.querySelector(".button-class").addEventListener('click', (e) => {
                let stringPurpose = $("#purpose-input")[0].value;
                if(stringPurpose.length === 0 ) {
                    $("#purpose-input")[0].checkValidity();
                } else {
                    let elem = document.createElement("input");
                    elem.type = "hidden";
                    elem.name = "purpose";
                    elem.value = stringPurpose;
                    e.srcElement.parentElement.appendChild(elem);
                    e.srcElement.parentElement.submit();
                }
            });
            let appId = app.id;
            let inputID = document.createElement('input');
            inputID.type = 'hidden';
            inputID.value = appId;
            inputID.name = 'id';
            form.appendChild(inputID);
            newTicket.appendChild(form);
            active.querySelector(".free-appointment-container").appendChild(newTicket);
        } else {
            prevDateTime = stringDate;
            active = slide.cloneNode(true);
            let newDateCon = dateCon.cloneNode(true);
            newDateCon.innerHTML = stringDate;
            let time = dateTime.time.hour+":"+dateTime.time.minute;
            let newTicket = ticket.cloneNode(true);
            newTicket.innerHTML = time;
            let form = formCon.cloneNode(true);
            form.querySelector(".button-class").addEventListener('click', (e) => {
               let stringPurpose = $("#purpose-input")[0].value;
               if(stringPurpose.length === 0 ) {
                   $("#purpose-input")[0].checkValidity();
               } else {
                   let elem = document.createElement("input");
                   elem.type = "hidden";
                   elem.name = "purpose";
                   elem.value = stringPurpose;
                   e.srcElement.parentElement.appendChild(elem);
                   e.srcElement.parentElement.submit();
               }
            });
            let appId = app.id;
            let inputID = document.createElement('input');
            inputID.type = 'hidden';
            inputID.value = appId;
            inputID.name = 'id';
            form.appendChild(inputID);
            newTicket.appendChild(form);
            active.querySelector(".free-appointment-container").appendChild(newTicket);
            active.querySelector(".date").innerHTML = stringDate;
            $('.scroll-container')[0].appendChild(active);
        }
    }
    $('.scroll-container')[0].className = "scroll-container";
    $('.scroll-container').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        adaptiveHeight: true
    });
}

function initAlert(alertType, message, button) {
    if(alertType.includes("successful")) {
        Notiflix.Report.Success(".",message + "<br><br>", button);
    } else {
        Notiflix.Report.Failure(".", message + "<br><br>", button);
    }
}

let makeAppointmentButton;

function setLocale(locale) {
    if(locale === "ru_RU") {
        makeAppointmentButton = "���������� �� ����";
    } else {
        makeAppointmentButton = "Make appointment";
    }
}

