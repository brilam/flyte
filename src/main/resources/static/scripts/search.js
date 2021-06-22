function search() {
    origin = document.getElementById("origin-location");
    destination = document.getElementById("destination-location");
    departure_date = document.getElementById("departure-date").value.replace("T", "") + ":00";

    console.log(origin.value);
    origin_id = -1
    destination_id = -1;

    // Pardon the bad code! :(
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "api/locations?names=" + origin.value  + "," + destination.value, true);
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            result = JSON.parse(xmlHttp.responseText);
            console.log(result);
            origin_id = result[0]['id'];
            destination_id = result[1]['id'];
            console.log("Departure Date: " + departure_date);
            console.log("Origin ID: " + origin_id);
            console.log("Destination ID: " + destination_id);
        }
    }
    xmlHttp.send();
}

function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

// Example POST method implementation from MDN
async function postData(url = '', data = {}) {
    const response = await fetch(url, {
      method: 'POST', 
      mode: 'cors', 
      cache: 'no-cache',
      credentials: 'same-origin', 
      headers: {
        'Content-Type': 'application/json',
        'X-CSRFToken': getCookie("csrftoken")
      },
      redirect: 'follow',
      referrerPolicy: 'no-referrer', 
      body: JSON.stringify(data) 
    });
    return response.json();
}