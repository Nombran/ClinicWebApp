Notiflix.Loading.Circle();
const allDepRequest = new XMLHttpRequest();
allDepRequest.responseType = "json";
let departmentChoosedId;

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
    let buttonCon = document.createElement("div");
    buttonCon.className = 'button-con';
    let editForm = document.createElement("form");
    editForm.className = "edit-form";
    editForm.action = "controller";
    editForm.method = "get";
    let deleteForm = document.createElement("form");
    deleteForm.className = "delete-form";
    deleteForm.action = "controller";
    deleteForm.method = "post";
    let submitEditInput = document.createElement("input");
    submitEditInput.type = "submit";
    submitEditInput.className = "blue";
    submitEditInput.value = editButton;
    editForm.appendChild(submitEditInput);
    let commandEdit = document.createElement("input");
    commandEdit.type = "hidden";
    commandEdit.name = "command";
    commandEdit.value = "edit_department_page";
    editForm.appendChild(commandEdit);
    editForm.appendChild(submitEditInput);

    let submitDeleteInput = document.createElement("input");
    submitDeleteInput.type = "submit";
    submitDeleteInput.className = "darkblue";
    submitDeleteInput.value = "Delete";
    deleteForm.appendChild(submitDeleteInput);
    let commandDelete = document.createElement("input");
    commandDelete.type = "hidden";
    commandDelete.name = "command";
    commandDelete.value = "delete_department";
    deleteForm.appendChild(commandDelete);
    deleteForm.appendChild(submitDeleteInput);

    buttonCon.appendChild(editForm);



    depBlock.appendChild(depImg);
    depBlock.appendChild(idInput);
    depBlock.appendChild(depName);
    depBlock.appendChild(buttonCon);
    let active = items.cloneNode(true);
    for (let dep of allDeps) {
        if (count < 4) {
            let stringDepName = dep.name;
            let depId = dep.id;
            let depImgSrc = "../clinic" + dep.imagePath;
            let newDepBlock = depBlock.cloneNode(true);
            newDepBlock.querySelector('.dep-img').style.backgroundImage = "url('" + depImgSrc + "')";
            newDepBlock.querySelector('.dep-name').innerHTML = stringDepName;
            let idHidden = document.createElement("input");
            idHidden.type = "hidden";
            idHidden.name = "id";
            idHidden.value = dep.id;
            newDepBlock.querySelector('.edit-form').appendChild(idHidden.cloneNode(true));
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
    Notiflix.Loading.Remove();
}

let editButton;

function setLocale(locale) {
    if(locale === "ru_RU") {
        editButton = "Изменить";
    } else {
        editButton = "Edit";
    }

}