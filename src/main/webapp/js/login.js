const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

let invlaidCommandMessage;
let noRightsMessage;

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});


function resultAlert(successMessage,failedMessage,resultType,resultMessage, continueMessage, buttonName) {
    if(resultType === "message.successful_registration") {
        Notiflix.Report.Success(successMessage,resultMessage+"<br><br>",buttonName);
    } else {
        Notiflix.Report.Failure(failedMessage,resultMessage+"<br><br>",buttonName);
    }
}

function errorAlert(error,message,button) {
    Notiflix.Report.Failure(error, message+"<br><br>", button);
}

function getLocalizeText(keyValue) {
    return "<fmt:message key=\'" + keyValue + "\' />";
}

