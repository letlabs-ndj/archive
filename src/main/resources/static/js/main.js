const dropArea = document.querySelector(".drag-area");
const dragText = document.querySelector(".header");
const browse = document.getElementById("browse");
const upload = document.querySelector(".upload");

let button = dropArea.querySelector(".button");
let input = dropArea.querySelector("input");

let file;
upload.disabled = true;

button.onclick = () => {
  input.click();
};// when browse
input.addEventListener("change", function () {
  file = this.files[0];
  dropArea.classList.add("active");
  displayFile();
});// when file is inside drag area
dropArea.addEventListener("dragover", (event) => {
  event.preventDefault();
  dropArea.classList.add("active");
  dragText.textContent = "Release to Upload";
  // console.log('File is inside the drag area');
});// when file leave the drag area
dropArea.addEventListener("dragleave", () => {
  dropArea.classList.remove("active");
  // console.log('File left the drag area');
  dragText.textContent = "Drag & Drop";
});// when file is dropped
dropArea.addEventListener("drop", (event) => {
  event.preventDefault();
  // console.log('File is dropped in drag area');
  dropArea.classList.remove("active");
  file = event.dataTransfer.files[0]; // grab single file even of user selects multiple files
  console.log(file);
  displayFile();
});

function displayFile() {
  let fileType = file.type;
  // console.log(fileType);
  let fileReader = new FileReader();
  fileReader.onload = () => {
  let fileURL = fileReader.result;
  dragText.textContent = file.name;
  browse.style.display = "none";
  upload.disabled = false;
  upload.classList.add('upload-active');
};
    fileReader.readAsDataURL(file);
}

function getCategories(){
  const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
  const archiveCategories = [];

  for (const checkbox of checkboxes) {
    const archiveCategory = {
      id: checkbox.value, // Assurez-vous que chaque case à cocher a une valeur unique qui correspond à l'identifiant de la catégorie d'archive
      category: checkbox.name // Assurez-vous que chaque case à cocher a un libellé qui correspond au nom de la catégorie d'archive
    };
    archiveCategories.push(archiveCategory);
  }

  const archiveCategoryList = {
    archiveCategories: archiveCategories
  };

  const jsonData = JSON.stringify(archiveCategoryList);
  console.log(jsonData);
  return jsonData;
}


upload.addEventListener('click', () => {
  var formData = new FormData();
  const jsonData = getCategories();
  const archive = file;

  formData.append('file', archive);
  formData.append('data', jsonData);

  fetch('http://localhost:8080/archive/upload-file', {
    method: 'POST',
    body: formData
  })
  .then((response) => {
    console.log(response.status)
    if(response.status==201){
      window.location.href = 'http://localhost:8080/admin';
    }else{
      console.log('failed registration')
    }
  })
});
