async function fetchDecodedPassword(credentialId) {
    let response = await fetch(`http://localhost:8080/decodePassword?credentialId=${credentialId}`);
    if (response.ok) {
        let data = await response.json();
        let resultData = JSON.parse(JSON.stringify(data));
        return resultData.password;
    }
    else {
        alert("HTTP-Error: " + response.status);
    }
}

