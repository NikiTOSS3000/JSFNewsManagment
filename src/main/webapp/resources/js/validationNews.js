function validateSave() {
    var title = document.getElementsByClassName('title')[0];
    var date = document.getElementsByClassName('date')[0];
    var brief = document.getElementsByClassName('brief')[0];
    var content = document.getElementsByClassName('cont')[0];
    if (title.value == '') {
        alert(titleMsg + ' ' + required);
        return false;
    }
    if (checkDate(date)) {
        alert(dateMsg + ' ' + invalid);
        return false;
    }
    if (brief.value == '') {
        alert(briefMsg + ' ' + required);
        return false;
    }
    if (content.value == '') {
        alert(contentMsg + ' ' + required);
        return false;
    }
    return true;
}

function checkDate(date) {
    curdt = date.value;
    curdtarr = curdt.split('/');
    isdt = true;
    k = 0;
    for (; k < curdtarr.length; k++) {
        if (isNaN(curdtarr[k])) {
            isdt = false;
            break;
        }
    }
    is_valid = isValidDate(curdtarr[1], curdtarr[0], curdtarr[2]);
    if (!(isdt & (curdtarr.length == 3) & is_valid)) {
        return true;
    }
    return false;
}

function isValidDate(day, month, year) {
    if (month < 1 || month > 12) {
        return false;
    }
    if (day < 1 || day > 31) {
        return false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) &&
            (day == 31)) {
        return false;
    }
    if (month == 2) {
        var leap = (year % 4 == 0 &&
                (year % 100 != 0 || year % 400 == 0));
        if (day > 29 || (day == 29 && !leap)) {
            return false;
        }
    }
    return true;
}

function validateCheckboxes() {
    chks = document.getElementsByClassName('checkboxes');
    i = 0;
    for (; i < chks.length; i++) {
        if (chks[i].checked) {
            return confirm(confirmSelected);
        }
    }
    alert(deleteSelected);
    return false;
}
