//replaceAll prototype 선언
String.prototype.includes = function (match) {
    return this.indexOf(match) !== -1;
}

String.prototype.replaceAll = function (org, dest) {
    return this.split(org).join(dest);
};
String.prototype.string = function (len) {
    var s = '', i = 0;
    while (i++ < len) {
        s += this;
    }
    return s;
};
String.prototype.zf = function (len) {
    return "0".string(len - this.length) + this;
};

String.prototype.nullReplace = function (replaceFormat) {
    return this.replace('null', replaceFormat);
};

/**
 * 좌측문자열채우기
 * @params
 *  - padLen : 최대 채우고자 하는 길이
 *  - padStr : 채우고자하는 문자(char)
 */
String.prototype.lpad = function (padLen, padStr) {
    var str = this;
    if (padStr.length > padLen) {
        console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    while (str.length < padLen)
        str = padStr + str;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
};

/**
 * 우측문자열채우기
 * @params
 *  - padLen : 최대 채우고자 하는 길이
 *  - padStr : 채우고자하는 문자(char)
 */
String.prototype.rpad = function (padLen, padStr) {
    var str = this;
    if (padStr.length > padLen) {
        console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    while (str.length < padLen)
        str += padStr;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}

//숫자 프로토타입으로 입력 길이만큼 앞에 0을 채운 문자열 반환
Number.prototype.fillZero = function (width) {
    var n = String(this);//문자열 변환
    return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;//남는 길이만큼 0으로 채움
}

//문자열 프로토타입으로 입력 길이만큼 앞에 0을 채운 문자열 반환
String.prototype.fillZero = function (width) {
    return this.length >= width ? this : new Array(width - this.length + 1).join('0') + this;//남는 길이만큼 0으로 채움
}

Date.prototype.format = function (f) {
    if (!this.valueOf()) return " ";

    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
        weekSName = ["일", "월", "화", "수", "목", "금", "토"],
        weeksEngName = ["sun", "mon", "tue", "wen", "thu", "fri", "sat"],
        d = this;

    return f.replace(/(yyyy|yy|YN|Y|MM|MN|M|dd|d|SE|EE|E|24HH|24H|hh|mm|ss|a\/p)/gi, function ($1) {
        switch ($1) {
            case "yyyy":
                return d.getFullYear();
            case "yy":
                return (d.getFullYear() % 1000).zf(2);
            case "Y":
                return d.getFullYear() + '년';
            case "YN":
                return d.getFullYear();
            case "MM":
                return (d.getMonth() + 1).zf(2);
            case "MN":
                return (d.getMonth() + 1);
            case "M":
                return (d.getMonth() + 1) + '월';
            case "dd":
                return d.getDate().zf(2);
            case "d":
                return d.getDate() + '일';
            case "SE":
                return weekSName[d.getDay()];
            case "EE":
                return weeksEngName[d.getDay()];
            case "E":
                return weekName[d.getDay()];
            case "24HH":
                return d.getHours().zf(2);
            case "24H":
                return d.getHours();
            case "hh":
                return ((d = d.getHours() % 12) ? d : 12).zf(2);
            case "mm":
                return d.getMinutes().zf(2);
            case "ss":
                return d.getSeconds().zf(2);
            case "a/p":
                return d.getHours() < 12 ? "오전" : "오후";
            default:
                return $1;
        }
    });
};

Date.prototype.addDays = function (days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
};


Date.prototype.addHours = function (hours) {
    var date = new Date(this.valueOf());
    date.setHours(date.getHours() + hours);
    return date;
};

Date.prototype.getWeekOfMonth = function () {
    var firstWeekday = new Date(this.getFullYear(), this.getMonth(), 1).getDay();
    var offsetDate = this.getDate() + firstWeekday - 1;
    return Math.floor(offsetDate / 7);
};

Number.prototype.zf = function (len) {
    return this.toString().zf(len);
};

Object.defineProperty(Array.prototype, 'group', {
    configurable: true,
    enumerable: false,
    value: function (key) {
        var map = {};
        this.forEach(function (e) {
            var k = e[key];
            map[k] = map[k] || [];
            map[k].push(e);
        });
        return Object.keys(map).map(function (k) {
            return {key: k, data: map[k]};
        });
    }
});

if (!Array.prototype.forEach) {
    Array.prototype.forEach = function (fun /*, thisp*/) {
        var len = this.length >>> 0,
            thisp, i;
        if (typeof fun != "function")
            throw new TypeError();

        thisp = arguments[1];
        for (i = 0; i < len; i++) {
            if (i in this)
                fun.call(thisp, this[i], i, this);
        }
    };
}

Array.prototype.moveIndex = function (from, to) {
    this.splice(to, 0, this.splice(from, 1)[0]);
    return this;
};


$.extend({
    range: function () {
        if (!arguments.length) {
            return [];
        }
        var min, max, step, tmp, a = [], i;
        if (arguments.length == 1) {
            min = 0;
            max = arguments[0] - 1;
            step = 1;
        } else {
            // default step to 1 if it's zero or undefined
            min = arguments[0];
            max = arguments[1] - 1;
            step = arguments[2] || 1;
        }
        // convert negative steps to positive and reverse min/max
        if (step < 0 && min >= max) {
            step *= -1;
            tmp = min;
            min = max;
            max = tmp;
            min += ((max - min) % step);
        }
        for (i = min; i <= max; i += step) {
            a.push(i);
        }
        return a;
    }
});

/**
 * @constructor
 */
var Common = function () {
    this.contextRoot = ($("meta[name='ctx']").attr("content") === '/') ? '' : $("meta[name='ctx']").attr("content");
    this.pages = {};
    this._isAuth = false;
    this._codeList = [];
    this._member = {};
}
Common.prototype.name = 'Common';
$.extend(Common.prototype, {
    constructor: Common,
    closeBrowser: function () {
        localStorage.clear();
        console.log('close Browser');
    },
    groupBy: function (xs, key) {
        return xs.reduce(function (rv, x) {
            (rv[x[key]] = rv[x[key]] || []).push(x);
            return rv;
        }, {});
    },
    getCookie: function (name) {
        var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value ? value[2] : null;
    },
    setCookie: function (name, value, day) {
        var date = new Date();
        date.setTime(date.getTime() + day * 60 * 60 * 24 * 1000);
        document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
    },
    deleteCookie: function (cookieName) {
        var expireDate = new Date();
        expireDate.setDate(expireDate.getDate() - 1);
        document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
    },
    beforeUnloadPage: function () {
        if (window.addEventListener) {
            window.addEventListener('beforeunload', this._setOnBeforeUnload);
        } else {
            window.attachEvent('onbeforeunload', this._setOnBeforeUnload);
        }
    },
    removeBeforeUnloadPage: function () {
        if (window.removeEventListener) {
            window.removeEventListener("beforeunload", this._setOnBeforeUnload);
        } else {
            window.detachEvent("onbeforeunload", this._setOnBeforeUnload);
        }
    },
    _setOnBeforeUnload: function (e) {
        e = e || window.event;
        // For IE and Firefox
        if (e) {
            e.returnValue = '가입 중 F5(새로고침) 클릭시 등록한 내용이 사라집니다.';
        }
        // For Safari
        return '가입 중 F5(새로고침) 클릭시 등록한 내용이 사라집니다.';
    },
    ajax: function (options) {
        if (options !== undefined) {
            var dataType = options.dataType || '',
                reqUrl = '';
            if (dataType === 'jsonp') {
                reqUrl = options.url || '';
            } else {
                reqUrl = this.contextRoot + options.url || this.contextRoot + '';
            }
        }
        return $.ajax({
            url: reqUrl,
            type: options.type || 'post',
            data: options.data || {},
            async: options.isAsync || true,
            dataType: options.dataType || 'json',
            cache: options.cache || false,
            traditional: options.traditional || false,
            statusCode: {
                400: function (xhr) {
                    console.log('입력 값을 확인 해 주세요. =>{}', xhr);
                },
                401: function (xhr) {
                    console.log('권한이 없습니다. =>{}', xhr);
                    common.movePage(null, null, '/login');
                },
                404: function (xhr) {
                    console.log('잘못 된 요청입니다. =>{}', xhr);
                },
                500: function (xhr) {
                    console.log('서버 오류가 발생하였습니다. =>{}', xhr);
                }
            },
            beforeSend: function (xhr) {
                var csrfToken = $("meta[name='_csrf']").attr("content"),
                    csrfHeader = $("meta[name='_csrf_header']").attr("content");
                if (csrfHeader !== '' && csrfHeader !== undefined && csrfToken !== '' && csrfToken !== undefined) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                }
                xhr.setRequestHeader('X-REQUEST-REFERER', location.pathname);
                if (typeof options.beforeSend === 'function') {
                    options.beforeSend(xhr);
                }
                common.blockUI(true);
            }
        }).done(options.callBack || function (res) {
            console.log('ajax callBack res', res);
        }).fail(options.error || function (xhr) {
            if (xhr.status === 400) {
                console.log('입력 값을 확인 해 주세요. =>{}', xhr);
            } else if (xhr.status === 401) {
                console.log('권한이 없습니다. =>{}', xhr);
                alert('Sesi Anda telah berakhir.');
                common.movePage(null, null, '/logout');
            } else if (xhr.status === 404) {
                console.log('잘못 된 요청입니다. =>{}', xhr);
            } else if (xhr.status === 500) {
                console.log('서버 오류가 발생하였습니다. =>{}', xhr);
            } else {
                if (xhr.status === 200) {
                    //common.movePage(null, null, '/logout');
                } else if (xhr.status === 0) {
                    console.log('요청 취소. =>{}', xhr);
                }
                console.log(xhr.statusText + ' =>{}', xhr);

            }
        }).always(function () {
            if (typeof options.complete === 'function') {
                options.complete();
            }
            common.blockUI(false);
        });
    },
    ajaxRequest: function (options) {
        if (options !== undefined) {
            var dataType = options.dataType || '',
                reqUrl = '';
            if (dataType === 'jsonp') {
                reqUrl = options.url || '';
            } else {
                reqUrl = this.contextRoot + options.url || this.contextRoot + '';
            }
            return $.ajax({
                xhr: function () {
                    var xhr = new window.XMLHttpRequest();
                    //Upload progress
                    xhr.upload.addEventListener("progress", function (evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            //Do something with upload progress
                            console.log(percentComplete);
                        }
                    }, false);
                    //Download progress
                    xhr.addEventListener("progress", function (evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            //Do something with download progress
                            console.log(percentComplete);
                        }
                    }, false);
                    return xhr;
                },
                url: reqUrl,
                type: options.type || 'post',
                data: options.data || {},
                async: options.isAsync || true,
                dataType: options.dataType || 'json',
                context: options.context || document.body,
                contentType: options.contentType || 'application/json',
                cache: options.cache || false,
                statusCode: {
                    400: function (xhr) {
                        console.log('입력 값을 확인 해 주세요. =>{}', xhr);
                    },
                    401: function (xhr) {
                        console.log('권한이 없습니다. =>{}', xhr);
                        common.movePage(null, null, '/login');
                    },
                    404: function (xhr) {
                        console.log('잘못 된 요청입니다. =>{}', xhr);
                    },
                    500: function (xhr) {
                        console.log('서버 오류가 발생하였습니다. =>{}', xhr);
                    }
                },
                beforeSend: function (xhr) {
                    var csrfToken = $("meta[name='_csrf']").attr("content"),
                        csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    if (csrfHeader !== '' && csrfHeader !== undefined && csrfToken !== '' && csrfToken !== undefined) {
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    }
                    xhr.setRequestHeader('X-REQUEST-REFERER', location.pathname);
                    if (typeof options.beforeSend === 'function') {
                        options.beforeSend(xhr);
                    }
                    common.blockUI(true);
                }
            }).done(options.callBack || function (res) {
                console.log('ajax callBack res', res);
            }).fail(options.error || function (xhr) {
                if (xhr.status === 400) {
                    console.log('입력 값을 확인 해 주세요. =>{}', xhr);
                } else if (xhr.status === 401) {
                    console.log('권한이 없습니다. =>{}', xhr);
                    common.movePage(null, null, '/logout');
                } else if (xhr.status === 404) {
                    console.log('잘못 된 요청입니다. =>{}', xhr);
                } else if (xhr.status === 500) {
                    console.log('서버 오류가 발생하였습니다. =>{}', xhr);
                } else {
                    if (xhr.status === 200) {
                        //common.movePage(null, null, '/logout');
                    } else if (xhr.status === 0) {
                        console.log('요청 취소. =>{}', xhr);
                    }
                    console.log(xhr.statusText + ' =>{}', xhr);

                }
            }).always(function () {
                if (typeof options.complete === 'function') {
                    options.complete();
                }
                common.blockUI(false);
            });
        }
    },
    ajaxFormRequest: function (options) {
        if (options !== undefined) {
            var reqUrl = this.contextRoot + options.url || this.contextRoot + '';
            return $.ajax({
                xhr: function () {
                    var xhr = new window.XMLHttpRequest();
                    //Upload progress
                    xhr.upload.addEventListener("progress", function (evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            //Do something with upload progress
                            console.log(percentComplete);
                        }
                    }, false);
                    //Download progress
                    xhr.addEventListener("progress", function (evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            //Do something with download progress
                            console.log(percentComplete);
                        }
                    }, false);
                    return xhr;
                },
                url: reqUrl,
                type: options.type || 'post',
                data: options.data || {},
                async: options.isAsync || true,
                processData: false,
                contentType: false,
                enctype: options.enctype || 'multipart/form-data',
                cache: options.cache || false,
                statusCode: {
                    400: function (xhr) {
                        console.log('입력 값을 확인 해 주세요. =>{}', xhr);
                    },
                    401: function (xhr) {
                        console.log('권한이 없습니다. =>{}', xhr);
                        common.movePage(null, null, '/logout');
                    },
                    404: function (xhr) {
                        console.log('잘못 된 요청입니다. =>{}', xhr);
                    },
                    500: function (xhr) {
                        console.log('서버 오류가 발생하였습니다. =>{}', xhr);
                    }
                },
                beforeSend: function (xhr) {
                    var csrfToken = $("meta[name='_csrf']").attr("content"),
                        csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    if (csrfHeader !== '' && csrfHeader !== undefined && csrfToken !== '' && csrfToken !== undefined) {
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    }
                    xhr.setRequestHeader('X-REQUEST-REFERER', location.pathname);
                    if (typeof options.beforeSend === 'function') {
                        options.beforeSend(xhr);
                    }
                    common.blockUI(true);
                }
            }).done(options.callBack || function (res) {
                console.log('ajax callBack res', res);
            }).fail(options.error || function (xhr) {
                if (xhr.status === 400) {
                    console.log('입력 값을 확인 해 주세요. =>{}', xhr);
                } else if (xhr.status === 401) {
                    console.log('권한이 없습니다. =>{}', xhr);
                    common.movePage(null, null, '/login');
                } else if (xhr.status === 404) {
                    console.log('잘못 된 요청입니다. =>{}', xhr);
                } else if (xhr.status === 500) {
                    console.log('서버 오류가 발생하였습니다. =>{}', xhr);
                } else {
                    console.log('처리 도중 오류가 발생 하였습니다. =>{}', xhr);
                    if (xhr.status === 200) {
                        common.movePage(null, null, '/logout');
                    }
                }
            }).always(function () {
                if (typeof options.complete === 'function') {
                    options.complete();
                }
                common.blockUI(false);
            });
        }
    },
    blockUI: function (_bool) {
        if (_bool) {
            console.log("로딩 중");
        } else {
            console.log("로딩 끝");
        }
    },
    getParameterByName: function (name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    },
    removeDuplicatedStrArray: function (arr) {
        var unique = [];
        $.each(arr, function (i, el) {
            if ($.inArray(el, unique) === -1) unique.push(el);
        });
        return unique;
    },
    getQuery: function (name) {
        var qs = location.search.split('?')[1], result = {};
        if (qs === undefined) {
            return {};
        } else {
            qs = qs.split('&');
        }
        for (var i = 0; i < qs.length; i++) {
            qs[i] = qs[i].split('=');
            result[qs[i][0]] = decodeURIComponent(qs[i][1]);
        }
        return result;
    },
    getHash: function () {
        var qs = location.hash, result = [];
        if (qs === undefined || qs === '') {
            return [];
        } else {
            qs = qs.split(',');
        }
        for (var i = 0; i < qs.length; i++) {
            result.push(decodeURIComponent(qs[i]));
        }
        return result;
    },
    movePage: function (parameter, value, pathname, replace) {
        parameter = parameter || '';
        replace = replace || false;
        if (typeof value === 'string') {
            value = value || '';
        } else if (typeof value === 'number') {
            value = value.toString() || '';
        } else {
            value = '';
        }

        if (pathname === undefined) {
            if (parameter === undefined || parameter === '') {
                if (replace) {
                    location.replace(location.origin + location.pathname);
                } else {
                    location.href = location.origin + location.pathname;
                }
            } else {
                if (replace) {
                    location.replace(location.origin + location.pathname + '?' + parameter + '=' + encodeURI(value));
                } else {
                    location.href = location.origin + location.pathname + '?' + parameter + '=' + encodeURI(value);
                }
            }
        } else {
            pathname = pathname || '/';
            if (parameter === undefined || parameter === '') {
                if (replace) {
                    location.replace(location.origin + this.contextRoot + pathname);
                } else {
                    location.href = location.origin + this.contextRoot + pathname;
                }
            } else {
                if (replace) {
                    location.replace(location.origin + this.contextRoot + pathname + '?' + parameter + '=' + encodeURI(value));
                } else {
                    location.href = location.origin + this.contextRoot + pathname + '?' + parameter + '=' + encodeURI(value);
                }
            }
        }
    },
    checkEmail: function (str) {
        if (typeof str != "undefined" && str != "") {
            //var format = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            var format = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (format.test(str)) {
                return true;
            }
        }
        return false;
    },
    transDate: function (str, type) {
        var output = "",
            date = str.replaceAll('-', '');

        if (type === "slash") {
            date = date.substring(2, date.length);
            for (var i = 0; i <= date.length - 1; i++) {
                output = output + date[i];
                if ((i + 1) % 2 == 0 && (date.length - 1) !== i)
                    output = output + "/";
            }
        } else if (type === "date") {
            if (date.length == 8) {
                output = date.substr(0, 4) + "/" + date.substr(4, 2) + "/" + date.substr(6, 2);
            }
        } else if (type === "colon") {
            if (date.length == 8) {
                output = date.substr(0, 4) + ":" + date.substr(4, 2) + ":" + date.substr(6, 2);
            }
        } else if (type === "hyphen") {
            if (date.length == 8) {
                output = date.substr(0, 4) + "-" + date.substr(4, 2) + "-" + date.substr(6, 2);
            }
        } else if (type === 'hyphen2') {
            if (date.length === 16) {
                output = date.substr(0, 4) + "-" + date.substr(4, 2) + "-" + date.substr(6, 2)
                    + ' ' + date.substr(8, 2) + ':' + date.substr(10, 2) + ':' + date.substr(12, 2);
            }
        } else {
            var year = date.substr(0, 4),
                month = date.substr(4, 2),
                day = date.substr(6, 2);
            output = year + "년 " + month + "월 " + day + "일";
        }
        return output;
    },
    formatDate: function (date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();
        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;
        return [year, month, day].join('-');
    },
    dateDiff: function (_date1, _date2) {
        var diffDate_1 = _date1 instanceof Date ? _date1 : new Date(_date1),
            diffDate_2 = _date2 instanceof Date ? _date2 : new Date(_date2),
            diff = diffDate_2.getTime() - diffDate_1.getTime();

        //diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth() + 1, diffDate_1.getDate());
        //diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth() + 1, diffDate_2.getDate());

        diff = diff / (1000 * 3600 * 24);
        console.log('a는 오늘로 부터 ' + diff + '일 전입니다.');
        return diff;
    },
    isIEBrowser: function () {
        var agent = navigator.userAgent.toLowerCase();
        console.log('agent', navigator);
        if ((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
            // ie일 경우
            return true;
        } else {
            // ie가 아닐 경우
            return false;
        }
    },
    isValidUrl: function (data) {
        data = data || '';
        var regex = /(http(s)?:\/\/)([a-z0-9\w]+\.*)+[a-z0-9]{2,4}/gi;
        return regex.test(data);
    },
    removeDuplicates: function (originalArray, prop) {
        var newArray = [], lookupObject = {};

        for (var i in originalArray) {
            lookupObject[originalArray[i][prop]] = originalArray[i];
        }

        for (var i in lookupObject) {
            newArray.push(lookupObject[i]);
        }
        return newArray;
    },
    listToTree: function (list) {
        var map = {}, node, roots = [], i;

        for (i = 0; i < list.length; i += 1) {
            map[list[i].id] = i; // initialize the map
            list[i].children = []; // initialize the children
        }

        for (i = 0; i < list.length; i += 1) {
            node = list[i];
            if (node.pid !== "0") {
                // if you have dangling branches check that map[node.parentId] exists
                list[map[node.pid]].children.push(node);
            } else {
                roots.push(node);
            }
        }
        return roots;
    },
    replaceHtmlTag: function (targetStr) {
        if (targetStr === null) {
            targetStr = '';
        }

        return targetStr.replace(/(<([^>]+)>)/ig, "");
    },
    getTemplateHTML: function (_id) {
        return document.getElementById(_id).innerHTML;
    },
    renderTemplateHTML: function (_htmlTemplate, _data) {
        var template = document.createElement('template');
        template.innerHTML = Mustache.render(_htmlTemplate, _data).trim();
        return template.content;
    },
    renderTemplateByIdHTML: function (_id, _data) {
        var template = document.createElement('template');
        template.innerHTML = Mustache.render(this.getTemplateHTML(_id), _data).trim();
        return template.content;
    },
    customDeferredFunction: function (opt) {
        opt = opt || {};
        var deferred = $.Deferred();
        console.log('시작');
        if (opt.execFunc && typeof opt.execFunc === 'function') {
            opt.execFunc();
        }
        try {
            if (opt.resolveFunc && typeof opt.resolveFunc === 'function') {
                opt.resolveFunc();
            }
            if (opt.return && typeof opt.return === 'function') {
                deferred.resolve(opt.return);
            } else if (opt.return && typeof opt.return === 'string') {
                deferred.resolve(opt.return);
            } else if (opt.return && typeof opt.return === 'object') {
                deferred.resolve(opt.return);
            } else {
                deferred.resolve('Success');
            }

        } catch (err) {
            if (opt.rejectFunc && typeof opt.rejectFunc === 'function') {
                opt.rejectFunc();
            }
            deferred.reject(err);
        }
        return deferred.promise();
    }
});
var common = new Common();

function removeChar(t) {
    t.val(t.val().replace(/[^0-9]/g, ''));
}

function onlyKorean(inpObj) {
    var pattern = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
    inpObj.value = inpObj.value.replace(pattern, '');
}

function notPermitKorean(inpObj) {
    var pattern = /[ㄱ-힣]/g;
    if (inpObj.value.match(pattern)) {
        //return false;
        inpObj.value = inpObj.value.replace(pattern, '');
    }
}


function datepickerDefault(_selector) {
    $.datepicker._gotoToday = function (id) {
        var today = new Date(),
            dateRef = $("<td><a>" + today.getDate() + "</a></td>");
        this._selectDay(id, today.getMonth(), today.getFullYear(), dateRef);
    };
    var options = {
        showOn: "focus", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
        buttonImage: common.contextRoot + "/images/design/icons/icon_calendar.png", // 버튼 이미지
        buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
        changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
        changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
        minDate: '-100y', // 현재날짜로부터 100년이전까지 년을 표시한다.
        nextText: '다음 달', // next 아이콘의 툴팁.
        prevText: '이전 달', // prev 아이콘의 툴팁.
        numberOfMonths: [1, 1], // 한번에 얼마나 많은 월을 표시할것인가. [2,3] 일 경우, 2(행) x 3(열) = 6개의 월을 표시한다.
        stepMonths: 1, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가.
        yearRange: 'c-100:c+10', // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
        showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다.
        currentText: '오늘 날짜', // 오늘 날짜로 이동하는 버튼 패널
        closeText: '닫기',  // 닫기 버튼 패널
        dateFormat: "yy-mm-dd", // 텍스트 필드에 입력되는 날짜 형식.
        showAnim: "slide", //애니메이션을 적용한다.
        showMonthAfterYear: true, // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
        firstDay: 1, // 월요일을 첫 번째 요일로 설정
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], // 요일의 한글 형식.
        dayNamesShort: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'] // 월의 한글 형식.
    };
    $(_selector).datepicker(options);
}

function datepickerSetDate(_selector, _date) {
    $.datepicker._gotoToday = function (id) {
        var today = new Date(),
            dateRef = $("<td><a>" + today.getDate() + "</a></td>");
        this._selectDay(id, today.getMonth(), today.getFullYear(), dateRef);
    };
    var options = {
        showOn: "focus", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
        buttonImage: common.contextRoot + "/images/design/icons/icon_calendar.png", // 버튼 이미지
        buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
        changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
        changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
        minDate: '-100y', // 현재날짜로부터 100년이전까지 년을 표시한다.
        nextText: '다음 달', // next 아이콘의 툴팁.
        prevText: '이전 달', // prev 아이콘의 툴팁.
        numberOfMonths: [1, 1], // 한번에 얼마나 많은 월을 표시할것인가. [2,3] 일 경우, 2(행) x 3(열) = 6개의 월을 표시한다.
        stepMonths: 1, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가.
        yearRange: 'c-100:c+10', // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
        showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다.
        currentText: '오늘 날짜', // 오늘 날짜로 이동하는 버튼 패널
        closeText: '닫기',  // 닫기 버튼 패널
        dateFormat: "yy-mm-dd", // 텍스트 필드에 입력되는 날짜 형식.
        showAnim: "slide", //애니메이션을 적용한다.
        showMonthAfterYear: true, // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
        dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], // 요일의 한글 형식.
        dayNamesShort: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'] // 월의 한글 형식.
    };
    $(_selector).datepicker(options).datepicker("setDate", _date);
}

function isMobileFunc() {
    var UserAgent = navigator.userAgent;
    if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null) {
        return true;
    } else {
        return false;
    }
}


/**
 * @constructor
 */
function DeviceMeta() {
}

DeviceMeta.prototype.name = 'DeviceMeta';

/**
 * @return {boolean}
 * @throws {Error}
 */
DeviceMeta.isMobile = function () {
    var userAgent = this.getUserAgent(),
        userAgentPart = userAgent.substr(0, 4);
    return /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(userAgent)
        || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(userAgentPart);
};

/**
 * @return {boolean}
 * @throws {Error}
 */
DeviceMeta.isMobileOrTablet = function () {
    var userAgent = this.getUserAgent(),
        userAgentPart = userAgent.substr(0, 4);

    return /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(userAgent)
        || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(userAgentPart);
};

/**
 * @return {string|null}
 * @throws {Error}
 */
DeviceMeta.getUserAgent = function () {
    var userAgent = navigator.userAgent
        || navigator.vendor
        || window.opera
        || null;

    if (!userAgent)
        throw new Error('Failed to look for user agent information.');

    return userAgent;
};
window.onbeforeunload = common.closeBrowser;

$.readyException = function (error) {
    console.error(error);
};

jQuery.fn.serializeObject = function () {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function () {
                    obj[this.name] = this.value.trim();
                });
            }//if ( arr ) {
        }
    } catch (e) {
        alert(e.message);
    } finally {
    }

    return obj;
};
