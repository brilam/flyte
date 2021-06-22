function search() {
    origin = document.getElementById("origin-location");
    destination = document.getElementById("destination-location");
    departure_date = document.getElementById("departure-date").value.replace("T", " ") + ":00";
    order_by_cost = document.getElementById("cost").checked;
    order_by_travel_time = document.getElementById("travel-time").checked;
    order_by_flight_num = document.getElementById("num-flights").checked;

    order_by = "";
    if (order_by_cost == true) {
        order_by = "cost";
    } else if (order_by_travel_time == true) {
        order_by = "time"
    } else if (order_by_flight_num == true) {
        order_by = "flights"
    }

    console.log("Order by: " + order_by);


    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "api/itinerary?departureDate=" + departure_date + "&originLocation=" + origin.value  + "&destinationLocation=" + destination.value + "&orderBy=" + order_by, true);
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
                    itinerary_col = document.createElement("td");
                    num_flights_col = document.createElement("td");
                    total_cost_col = document.createElement("td");
                    travel_time_col = document.createElement("td");
                    departure_time_col = document.createElement("td");
                    arrival_time_col = document.createElement("td");
                    flight_path_col = document.createElement("td");


                    itinerary_col.append(result[index]["itineraryId"]);
                    num_flights_col.append(result[index]["flights"].length);
                    total_cost_col.append("$" + result[index]["totalCost"]);
                    travel_time_col.append(toHoursAndMins(result[index]["totalTime"]));
                    flights = result[index]["flights"];
                    departure_time_col.append(flights[0]["departureDate"]);
                    arrival_time_col.append(flights[flights.length - 1]["arrivalDate"]);
                    
                    if (flights.length != 1) {
                        for (var flight_index = 0; flight_index  < flights.length; flight_index++) {
                            if (flight_index != flights.length - 1) {
                                flight_path_col.append(flights[flight_index]["origin"]["name"]  + "->")
                            } else {
                                flight_path_col.append(flights[flight_index]["origin"]["name"]  + "->")
                                flight_path_col.append(flights[flight_index]["destination"]["name"]);
                            }
                        }
                    } else {
                        flight_path_col.append(flights[0]["origin"]["name"]  + "->")
                        flight_path_col.append(flights[0]["destination"]["name"])
                    }


                    row.append(itinerary_col);
                    row.append(num_flights_col);
                    row.append(total_cost_col);
                    row.append(travel_time_col);
                    row.append(departure_time_col);
                    row.append(arrival_time_col);
                    row.append(flight_path_col);
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


function toHoursAndMins(timeInMs) {
    var milliseconds = Math.floor((timeInMs % 1000) / 100),
    seconds = Math.floor((timeInMs / 1000) % 60),
    minutes = Math.floor((timeInMs / (1000 * 60)) % 60),
    hours = Math.floor((timeInMs / (1000 * 60 * 60)) % 24);

    hours = (hours < 10) ? "0" + hours : hours;
    minutes = (minutes < 10) ? "0" + minutes : minutes;
    seconds = (seconds < 10) ? "0" + seconds : seconds;
    
    
    return hours + "h" + (minutes == "00" ? "": minutes + "m");
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