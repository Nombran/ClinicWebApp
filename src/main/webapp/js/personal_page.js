let appointments;
let doctors;

function initData(apps,docs) {
    appointments = apps;
    doctors = docs;
    console.log(appointments);
}

function initAlert(alertType, message, button) {
    if(alertType === "message.successful") {
        Notiflix.Report.Success("",message + "<br><br>", button);
    } else {
        Notiflix.Report.Failure("", message + "<br><br>", button);
    }
}
