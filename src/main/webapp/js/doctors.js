$(document).ready(() => {
        const request = new XMLHttpRequest();
        request.responseType = "json";
        request.addEventListener("readystatechange", () => {
            if (request.readyState === 4 && request.status === 200) {
                let obj = request.response;
                initDocBlocks(obj);
            }
        });
        let url = "http://localhost:8087/clinic/controller?command=get_all_doctors";
        request.open("GET", url, true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send();
    }
);


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
    let docCon = document.createElement('div');
    docCon.className = 'button-container';
    let editForm = document.createElement('form');
    editForm.action = "controller";
    editForm.method = "post";
    editForm.className = "doc-button";
    let editInput = document.createElement('input');
    editInput.type = "submit";
    editInput.value = EditButton;
    editInput.className = "orange";
    let hiddenEditInput = document.createElement('input');
    hiddenEditInput.type = "hidden";
    hiddenEditInput.name = "command";
    hiddenEditInput.value = "edit_doctor_page";
    let hiddenIdInput = document.createElement('input');
    hiddenIdInput.type = "hidden";
    hiddenIdInput.name = "id";
    hiddenIdInput.id = "edit-doctor-id";
    editForm.appendChild(editInput);
    editForm.appendChild(hiddenIdInput);
    editForm.appendChild(hiddenEditInput);
    docCon.appendChild(editForm);

    let deleteForm = document.createElement('form');
    deleteForm.action = "controller";
    deleteForm.method = "post";
    deleteForm.className = "doc-button";
    let deleteInput = document.createElement('input');
    deleteInput.type = "submit";
    deleteInput.value = deleteButton;
    deleteInput.className = "red";
    let hiddenDeleteInput = document.createElement('input');
    hiddenDeleteInput.type = "hidden";
    hiddenDeleteInput.name = "command";
    hiddenDeleteInput.value = "delete_doctor";
    let hiddenDocIdInput = document.createElement('input');
    hiddenDocIdInput.type = "hidden";
    hiddenDocIdInput.name = "id";
    hiddenDocIdInput.id = "delete-doc-id";
    deleteForm.appendChild(hiddenDocIdInput);
    deleteForm.appendChild(deleteInput);
    deleteForm.appendChild(hiddenDeleteInput);
    docCon.appendChild(deleteForm);



    categBlock.className = 'category';
    docInfoCon.appendChild(surnameBlock);
    docInfoCon.appendChild(nameBlock);
    docInfoCon.appendChild(specBlock);
    docInfoCon.appendChild(categBlock);
    docItem.appendChild(docImg);
    docItem.appendChild(docInfoCon);
    docItem.appendChild(docCon);
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
            newDocItem.querySelector('#delete-doc-id').value = id;
            newDocItem.querySelector('#edit-doctor-id').value = id;
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

let deleteButton;
let EditButton;


function setLocale(locale) {
    if(locale === "ru_RU") {
        deleteButton = "Удалить";
        EditButton = "Изменить";
        console.log(deleteButton + EditButton);
    } else {
        deleteButton = "Delete";
        EditButton = "Edit";
    }

}