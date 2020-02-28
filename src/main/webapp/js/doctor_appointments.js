const freeAppsRequest = new XMLHttpRequest();
freeAppsRequest.responseType = "json";

const activeTicketsRequest = new XMLHttpRequest();
activeTicketsRequest.responseType = "json";

$(document).ready(() => {
    freeAppsRequest.addEventListener("readystatechange", () => {
        if (freeAppsRequest.readyState === 4 && freeAppsRequest.status === 200) {
            let obj = freeAppsRequest.response;
            initFreeBlock(obj);
        }
    });
    let url = "http://localhost:8087/clinic/controller?command=get_free_appointments";
    freeAppsRequest.open("GET", url, true);
    freeAppsRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    freeAppsRequest.send();

    activeTicketsRequest.addEventListener("readystatechange", () => {
        if (activeTicketsRequest.readyState === 4 && activeTicketsRequest.status === 200) {
            let obj = activeTicketsRequest.response;
            let customers = obj.doctor_customers;
            let apps = obj.doctor_future_appointments;
            initActivetickets(apps,customers);
        }
    });
    let url2 = "http://localhost:8087/clinic/controller?command=get_active_tickets_info";
    activeTicketsRequest.open("GET", url2, true);
    activeTicketsRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    activeTicketsRequest.send();
});

function initAlert(alertType, message, button) {
    if(alertType.includes("successful")) {
        Notiflix.Report.Success(".",message + "<br><br>", button);
    } else {
        Notiflix.Report.Failure(".", message + "<br><br>", button);
    }
}

function initFreeBlock(freeApps) {
    if(freeApps.length === 0 ) {
        $('.scroll-free')[0].innerHTML = "<h3 style='color: white; margin-top: 2vh;'>Here will be your free tickets</h3>";
    }
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
    command.value = 'delete_appointment';
    formCon.appendChild(command);
    let submitInput = document.createElement('input');
    submitInput.type = 'submit';
    submitInput.value = deleteTicketButton;
    formCon.appendChild(submitInput);
    let active;
    for(let app of freeApps) {
        let dateTime = app.dateTime;
        let stringDate = dateTime.date.year+"-"+dateTime.date.month+"-"+dateTime.date.day;
        if(prevDateTime == stringDate) {
            let time = dateTime.time.hour+":"+dateTime.time.minute;
            let newTicket = ticket.cloneNode(true);
            newTicket.innerHTML = time;
            let form = formCon.cloneNode(true);
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
            let appId = app.id;
            let inputID = document.createElement('input');
            inputID.type = 'hidden';
            inputID.value = appId;
            inputID.name = 'id';
            form.appendChild(inputID);
            newTicket.appendChild(form);
            active.querySelector(".free-appointment-container").appendChild(newTicket);
            active.querySelector(".date").innerHTML = stringDate;
            $('.scroll-free')[0].appendChild(active);
        }
    }
    $('.scroll-free').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        adaptiveHeight: true
    });
}

function initActivetickets(appointments, customers) {
    appointments = JSON.parse(appointments);
    customers = JSON.parse(customers);
    if(appointments.length === 0 ) {
        $('.scroll-busy')[0].innerHTML = "<h3 style='margin-top: 2vh;'>Here will be your active tickets</h3>";
    }
    let prevDateTime;
    let sliderCon = document.createElement('div');
    sliderCon.className = "slider-container";
    let dayCon = document.createElement('h1');
    dayCon.className = "day-con";
    let busyAppCon = document.createElement('div');
    busyAppCon.className = 'busy-appointment-container';
    let appointmentCon = document.createElement('div');
    appointmentCon.className = 'busy-appointment';
    let ticketCon = document.createElement('div');
    ticketCon.className = 'ticket-time';
    let ticketInfoCon = document.createElement('div');
    ticketInfoCon.className = 'busy-ticket-info';
    let clientCon = document.createElement('div');
    clientCon.className = 'busy-ticket-client';
    let descriptionCon = document.createElement('div');
    descriptionCon.className = "description";
    sliderCon.appendChild(dayCon);
    sliderCon.appendChild(busyAppCon);
    ticketInfoCon.appendChild(clientCon);
    ticketInfoCon.appendChild(descriptionCon);
    appointmentCon.appendChild(ticketCon);
    appointmentCon.appendChild(ticketInfoCon);
    let active;
    for(let app of appointments) {
        let dateTime = app.dateTime;
        let stringDate = dateTime.date.year+"-"+dateTime.date.month+"-"+dateTime.date.day;
        if(prevDateTime === stringDate) {
            let time = dateTime.time.hour+":"+dateTime.time.minute;
            let ticketNode = appointmentCon.cloneNode(true);
            ticketNode.querySelector(".ticket-time").innerHTML = time;
            let curClient;
            for(let client of customers) {
                if(client.id === app.customerId) {
                    curClient = client;
                    break;
                }
            }
            let name = curClient.surname + " " + curClient.name + " " + curClient.lastname;
            ticketNode.querySelector(".busy-ticket-client").innerHTML = name;
            let describ = app.purpose;
            if(describ !== undefined)
            ticketNode.querySelector(".description").innerHTML = describ;
            active.querySelector(".busy-appointment-container").appendChild(ticketNode);
        } else {
            prevDateTime = stringDate;
            active = sliderCon.cloneNode(true);
            active.querySelector(".day-con").innerHTML = stringDate;
            let time = dateTime.time.hour+":"+dateTime.time.minute;
            let ticketNode = appointmentCon.cloneNode(true);
            ticketNode.querySelector(".ticket-time").innerHTML = time;
            let curClient;
            for(let client of customers) {
                if(client.id === app.customerId) {
                    curClient = client;
                    break;
                }
            }
            let name = curClient.surname + " " + curClient.name + " " + curClient.lastname;
            ticketNode.querySelector(".busy-ticket-client").innerHTML = name;
            let describ = app.purpose;
            if(describ != undefined)
            ticketNode.querySelector(".description").innerHTML = describ;
            active.querySelector(".busy-appointment-container").appendChild(ticketNode);
            $(".scroll-busy")[0].appendChild(active);
        }
    }
    $('.scroll-busy').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        adaptiveHeight: true
    });
}
let deleteTicketButton;

function setLocale(locale) {
    if(locale === "ru_RU") {
        deleteTicketButton = "Удалить талон";
    } else {
        deleteTicketButton = "Delete ticket";
    }
}