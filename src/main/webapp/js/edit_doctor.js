let picture;

document.querySelector("html").classList.add('js');

var fileInput  = document.querySelector( ".input-file" ),
    button     = document.querySelector( ".input-file-trigger" ),
    the_return = document.querySelector(".file-return");

button.addEventListener( "keydown", function( event ) {
    if ( event.keyCode == 13 || event.keyCode == 32 ) {
        fileInput.focus();
    }
});
button.addEventListener( "click", function( event ) {
    fileInput.focus();
    return false;
});
fileInput.addEventListener( "change", function( event ) {
    the_return.innerHTML = event.target.value.split( '\\' ).pop();
    if(event.target.value.length === 0) {
        $("#dep-img")[0].src = "./img/default-avatar.png";
        picture = undefined;
        return;
    }
    let tgt = event.target || window.event.srcElement,
        files = tgt.files;
    if (FileReader && files && files.length) {
        let fr = new FileReader();
        fr.onload = function () {
            document.getElementById("dep-img").src = fr.result;
        };
        fr.readAsDataURL(files[0]);
        picture = files[0];
        if(!isFileImage(picture)) {
            Notiflix.Report.Failure("Upload image error!", "Not a image file"+"<br><br>", "Submit");
        }
    }
});

function isFileImage(file) {
    const acceptedImageTypes = ['image/jpg', 'image/jpeg', 'image/png'];

    return file && acceptedImageTypes.includes(file['type']);
}

function initAlert(alertType, message, button) {
    if(alertType === "message.successful_creating") {
        Notiflix.Report.Success("",message + "<br><br>", button);
    } else {
        Notiflix.Report.Failure("", message + "<br><br>", button);
    }
}

$(document).ready(() => {
    const allDepRequest = new XMLHttpRequest();
    allDepRequest.responseType = "json";
    allDepRequest.addEventListener("readystatechange", () => {
        if (allDepRequest.readyState === 4 && allDepRequest.status === 200) {
            let obj = allDepRequest.response;
            for(let val of obj) {
                let optional = document.createElement("option");
                optional.value = val.id;
                optional.innerHTML = val.name;
                optional.id = "dep-" + val.id;
                $("#department-select")[0].appendChild(optional);
            }
        }
    });
    let url = "http://localhost:8087/clinic/controller?command=get_all_departments";
    allDepRequest.open("GET", url, true);
    allDepRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    allDepRequest.send();
});

function initInputs(doctor,user) {
    setTimeout( () => {$("input[name = 'login']")[0].value = user.login;
        $("input[name = 'password']")[0].value = user.password;
        $("input[name = 'email']")[0].value = user.email;
        $("input[name = 'name']")[0].value = doctor.name;
        $("input[name = 'surname']")[0].value = doctor.surname;
        $("input[name = 'lastname']")[0].value = doctor.lastname;
        $("input[name = 'specialization']")[0].value = doctor.specialization;
        $("input[name = 'category']")[0].value = doctor.category;
        let fileHidden = document.createElement("input");
        fileHidden.type = "hidden";
        fileHidden.name = "prevImage";
        fileHidden.value = doctor.imagePath;
        $(".account-data")[0].appendChild(fileHidden);

        let idHidden = document.createElement("input");
        idHidden.type = "hidden";
        idHidden.name = "doctorId";
        idHidden.value = doctor.id;
        $(".account-data")[0].appendChild(idHidden);


        let userIdHidden = document.createElement("input");
        userIdHidden.type = "hidden";
        userIdHidden.name = "id";
        userIdHidden.value = user.id;
        $(".account-data")[0].appendChild(userIdHidden);

        let depId = doctor.departmentId;
        document.querySelector('#department-select').value = depId;
        },100);
}

$(".addDoctorForm").submit(() => {
    if(picture === undefined) {
        $("#dep-img")[0].removeAttr('src');
    }
    if(!isFileImage(picture)) {
        Notiflix.Report.Failure("Upload image error!", "Not a image file"+"<br><br>", "Submit");
        return false;
    }
    return true;
});