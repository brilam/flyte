function search() {
    origin = document.getElementById("origin-location");
    destination = document.getElementById("destination-location");
    departure_date = document.getElementById("departure-date").value.replace("T", " ") + ":00";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "api/itinerary?departureDate=" + departure_date + "&originLocation=" + origin.value  + "&destinationLocation=" + destination.value, true);
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            result = JSON.parse(xmlHttp.responseText);
            results = document.getElementById("results");
            table = document.getElementsByClassName("table")[0];
            search_results = document.getElementById("search-results");
            result_msg = document.getElementById("results-msg");

            if (result.length > 0) {
                // Hide search-results section and message if they were previously displayed
                if (search_results.style.display == "block") {
                    search_results.style.display = "none";
                    table.style.display = "none";
                    results.innerHTML  = '';
                }

                // Creates the table rows
                for (var index = 0; index < result.length; index++) {
                    row = document.createElement("tr");
                    itinerary_col = document.createElement("th");
                    total_cost_col = document.createElement("th");
                    total_time_col = document.createElement("th");
                    itinerary_col.append(result[index]["itineraryId"]);
                    total_cost_col.append(result[index]["totalCost"]);
                    total_time_col.append(result[0]["totalTime"]);
                    row.append(itinerary_col);
                    row.append(total_cost_col);
                    row.append(total_time_col);
                    results.append(row);

                    console.log("Itinerary ID: " + result[index]["itineraryId"]);
                    console.log("Total Cost: " + result[index]["totalCost"]);
                }
                // Display results
                table.style.display = "table";
                result_msg.innerHTML = "We are back with " + result.length + " results!";

            } else {
                result_msg.innerHTML = "No results found!";
                // Hide the table if not results found
                table.style.display = "none";
            }
            // In all cases, show the search results display block (since message must be shown)
            search_results.style.display = "block";
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