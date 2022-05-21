// getting all required elements
const searchWrapper = document.querySelector(".search-input");
const inputBox = searchWrapper.querySelector("input");
const suggBox = document.getElementById("autocom-box");
console.log(suggBox);
let button = document.getElementById('search');

// if user press any key and release
inputBox.onkeyup = (e)=>{
    let userData = e.target.value; //user enetered data
    let emptyArray = [];
    if(userData){
        let counter = 0;
        for(let i = 0; i<suggestions.length; i++){
            if(suggestions[i].toLocaleLowerCase().startsWith(userData.toLocaleLowerCase())){
                emptyArray.push(suggestions[i]);
                counter++;
                if(counter == 6)
                    break;
            }
        }
        emptyArray = emptyArray.map((data)=>{
            // passing return data inside li tag
            return data = `<li>${data}</li>`;
        });
        searchWrapper.classList.add("active"); //show autocomplete box
        showSuggestions(emptyArray);
        let allList = suggBox.querySelectorAll("li");
        for (let i = 0; i < allList.length; i++) {
            //adding onclick attribute in all li tag
            allList[i].setAttribute("onclick", "select(this)");
        }
        console.log(suggBox);
    }else{
        searchWrapper.classList.remove("active"); //hide autocomplete box
        suggBox.classList.remove("active");
        suggBox.classList.add("hide");
    }
}

function select(element){
    let selectData = element.textContent;
    inputBox.value = selectData;
    button.click();
    searchWrapper.classList.remove("active");
}

function showSuggestions(list){
    suggBox.classList.remove("hide");
    suggBox.classList.add("active");
    let listData;
    if(!list.length){
        userValue = inputBox.value;
        listData = `<li>${userValue}</li>`;
    }else{
      listData = list.join('');
    }
    suggBox.innerHTML = listData;
}