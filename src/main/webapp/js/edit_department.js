$(document).ready(()=>{
    document.querySelector("html").classList.add('js');
});

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

function initInputs(department) {
    $("input[name='name']")[0].value = department.name;
    $("input[name='phone']")[0].value = department.phone;
    $("textarea[name='description']")[0].value = department.description;
    let fileHidden = document.createElement("input");
    fileHidden.type = "hidden";
    fileHidden.name = "prevImage";
    fileHidden.value = department.imagePath;
    $(".content-con")[0].appendChild(fileHidden);

    let idHidden = document.createElement("input");
    idHidden.type = "hidden";
    idHidden.name = "id";
    idHidden.value = department.id;
    $(".content-con")[0].appendChild(idHidden);
}

function initAlert(alertType, message, button) {
    if(alertType.includes("successful")) {
        Notiflix.Report.Success(".",message + "<br><br>", button);
    } else {
        Notiflix.Report.Failure(".", message + "<br><br>", button);
    }
}

function isFileImage(file) {
    const acceptedImageTypes = ['image/jpg', 'image/jpeg', 'image/png'];

    return file && acceptedImageTypes.includes(file['type']);
}

$(".addDepartmentForm").submit(() => {
    if(picture === undefined) {
        $("#dep-img")[0].removeAttr('src');
    }
    if(!isFileImage(picture)) {
        Notiflix.Report.Failure("Upload image error!", "Not a image file"+"<br><br>", "Submit");
        return false;
    }
    return true;
});

fileInput.addEventListener( "change", function( event ) {
    the_return.innerHTML = event.target.value.split( '\\' ).pop();
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