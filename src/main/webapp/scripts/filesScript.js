function setPath(event) {
    const value = event.target.innerHTML;
    let url;
    const param = '?path=';
    if (window.location.href.includes(param)) {
        url = window.location.href + `${value}/`;
    } else {
        url = window.location.href + `${param}/${value}/`;
    }
    window.location.replace(url);
}

function getFile(event) {
    const queryParams = getQueryParams();
    const path = queryParams["path"];
    const origin = window.location.origin;
    const value = event.target.innerHTML;
    const url = `${origin}/download?path=${path}${value}`;

    window.location.replace(url);
}

function moveBack() {
    const queryParams = getQueryParams();
    const pathItems = queryParams['path'].split('/');
    queryParams['path'] = pathJoin(pathItems.slice(0, pathItems.length - 2));

    const queryString = Object.keys(queryParams)
        .map(key => `${key}=${queryParams[key]}`)
        .join('&');

    const url = `${window.location.origin}${window.location.pathname}?${queryString}`;
    window.location.replace(url);
}

function pathJoin(parts) {
    const separator = '/';
    const replace = new RegExp(`\/+`, 'g');
    return parts.join(separator).replace(replace, separator) + separator;
}

function getQueryParams() {
    const queryParams = {};
    window.location.search
        .substring(1)
        .split("&")
        .map(function (param) {
            return {
                paramName: param.split("=")[0],
                value: param.split("=")[1],
            };
        })
        .forEach(({paramName, value}) => queryParams[paramName] = value);

    return queryParams;
}

function logout() {
    fetch('/logout', { method: 'POST', redirect: 'follow' })
        .then(response => {
            if (response.redirected) {
                window.location.replace(response.url);
            }
        });
}