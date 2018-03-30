// show only the reviews section
var r = document.getElementById('other_reviews');
if (r) {
    document.body.innerHTML = '';
    document.body.appendChild(r);
}

// disables links, given a class name
function disable(clazz) {
    var eles = document.getElementsByClassName(clazz);
    for (var i = 0; i < eles.length; i++) {
        eles[i].onclick = (e) => e.preventDefault();
    }
}

// processes the page to remove comment footers and disable certain links
function processPage() {
    var r = document.getElementsByClassName('reviewFooter');
    for (var i = 0; i < r.length; i++) {
        var lc = r[i].querySelector('.likesCount'); r[i].innerHTML = lc.innerHTML;
    }
    disable('user');
    disable('reviewDate');
    disable('imgcol');
    disable('actionLinkLite');
}

// executes processPage after the completion of every ajax call
XMLHttpRequest.prototype.realSend = XMLHttpRequest.prototype.send;
XMLHttpRequest.prototype.send = function (value) {
    this.addEventListener('load', () => processPage(), false);
    this.realSend(value);
};

processPage();
