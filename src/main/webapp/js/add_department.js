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
            Notiflix.Report.Failure(HeaderText, textFooter+"<br><br>", "Submit");
        }
    }
});

$(".addDepartmentForm").submit(() => {
    if(!isFileImage(picture)) {
        Notiflix.Report.Failure(HeaderText, textFooter+"<br><br>", "Submit");
        return false;
    }
    return true;
});

function isFileImage(file) {
    const acceptedImageTypes = ['image/jpg', 'image/jpeg', 'image/png'];

    return file && acceptedImageTypes.includes(file['type'])
}

function initAlert(alertType, message, button) {
    if(alertType.includes("successful")) {
        Notiflix.Report.Success(".",message + "<br><br>", button);
    } else {
        Notiflix.Report.Failure(".", message + "<br><br>", button);
    }
}

let HeaderText;
let textFooter;

function setLocale(locale) {
    if(locale === "ru_RU") {
        HeaderText = "Ошибка при загрузке изображения";
        textFooter = "Изображение не выбрано или выбран неверный файл!"
    } else {
        HeaderText = "Upload image error!";
        textFooter = "Not a image file"
    }
}