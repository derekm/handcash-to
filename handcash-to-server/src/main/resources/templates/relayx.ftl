<!DOCTYPE html>
<html>

<head>
    <!-- Font -->
    <link rel="dns-prefetch" href="//fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css?family=Rubik:300,400,500" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Overpass+Mono" rel="stylesheet">

    <!-- Add icon library -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" type="text/css" href="relayx.css">
    <script type="application/javascript">
window.onload = function() {
    setupCopyHandler ('legacyCopyLabel','legacy');
};
function setupCopyHandler (anchorId, elementToCopy) {
    var copyHandler = document.getElementById(anchorId);
    copyHandler.addEventListener('click', function () {
        copyText(document.getElementById(elementToCopy));
    });
}
function copyText(element) {
    var range, selection;

    if (document.body.createTextRange) {
        range = document.body.createTextRange();
        range.moveToElementText(element);
        range.select();
    } else if (window.getSelection) {
        selection = window.getSelection();
        range = document.createRange();
        range.selectNodeContents(element);
        selection.removeAllRanges();
        selection.addRange(range);
    }

    try {
        document.execCommand('copy');
        updateStatus ('copied to clipboard', 'info');
    }
    catch (err) {
        alert('Unable to copy the address');
    }
}
function updateStatus (message, type) {
    var col;
    switch (type) {
        case 'error':
            col = 'bisque';
            break;
        case 'warning':
            col = 'yellow';
            break;
        default:
            col = 'whitesmoke';
    }
    document.getElementById('status').innerHTML = message;
    document.getElementById('status').style.background = col;
    document.getElementById('status').style.display='block'
}
    </script>
</head>

<body>
<div id="popup">
    <div id="logo">
        <a href="https://www.relayx.io" target="_blank"><img src="https://relayx.io/dist/assets/logo@2x.png" height="24" width="auto"></a>
    </div>
    <div class="centerbox">
        <strong style="font-size:24px;color:whitesmoke;">${handle}</strong>
        <div class="clearfix"></div>
        <div style="display:none" id="status" class="status"></div>
        <div class="clearfix"></div>
        <div id="addresses">
            <div class="code-block">
                <h5 class="alignleft">Bitcoin SV Address</h5>
            </div>
            <div>
                <a id="legacyCopyLabel" class="alignright">COPY</a>
            </div>
            <div class="clearfix"></div>
            <div class="address">
                <p id="qrcode"><img src="/${model.receivingAddress}/qr"></img></p>
<#setting url_escaping_charset="UTF-8">
<#assign bitcoinUri="bitcoin:${model.receivingAddress}?sv&req-sv">
<!--                <p id="qrcode"><img src="/${bitcoinUri?url}/qr"></img></p> -->
                <p id="legacy">${model.receivingAddress}</p>
            </div>
            <p />
            <a href="${bitcoinUri}">${bitcoinUri}</a>
        </div>
    </div>
    <div id="footer" class="footer">
        <p>
            <a href="http://handcash.to/$my_handle">Support this site here: handcash.to/$my_handle</a>
        </p>
    </div>
</div>
</body>

</html>