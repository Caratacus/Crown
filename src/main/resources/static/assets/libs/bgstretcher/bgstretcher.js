/*
	Background Stretcher jQuery Plugin
	� 2011 ajaxBlender.com
	For any questions please visit www.ajaxblender.com 
	or email us at support@ajaxblender.com
	
	Version: 2.0.1
*/

;(function ($) {
    /*  Variables  */
    var container = null;
    var allLIs = '', containerStr = '';

    var element = this;
    var _bgStretcherPause = false;
    var _bgStretcherAction = false;
    var _bgStretcherTm = null;
    var random_line = new Array();
    var random_temp = new Array();
    var r_image = 0;
    var swf_mode = false;
    var img_options = new Array();

    $.fn.bgStretcher = function (settings) {

        if ($('.bgstretcher-page').length || $('.bgstretcher-area').length) {
            if (typeof(console) !== 'undefined' && console != null) console.log('More than one bgStretcher');
            return false;
        }
        settings = $.extend({}, $.fn.bgStretcher.defaults, settings);
        $.fn.bgStretcher.settings = settings;

        function _build(body_content) {
            if (!settings.images.length) {
                return;
            }

            _genHtml(body_content);

            containerStr = '#' + settings.imageContainer;
            container = $(containerStr);
            allLIs = '#' + settings.imageContainer + ' LI';
            $(allLIs).hide().css({'z-index': 1, overflow: 'hidden'});

            if (!container.length) {
                return;
            }
            $(window).resize(function () {
                _resize(body_content)
            });

            _resize(body_content);

            var stratElement = 0;
            /*  Rebuild images for simpleSlide  */
            if (settings.transitionEffect == 'simpleSlide') {
                if (settings.sequenceMode == 'random') {
                    if (typeof(console) !== 'undefined' && console != null) {
                        console.log('Effect \'simpleSlide\' don\'t be to use with mode random.');
                        console.log('Mode was automaticly set in normal.');
                    }
                }
                $(allLIs).css({'float': 'left', position: 'static'});
                $(allLIs).show();
                if ($.fn.bgStretcher.settings.slideDirection == 'NW' || $.fn.bgStretcher.settings.slideDirection == 'NE') {
                    $.fn.bgStretcher.settings.slideDirection = 'N';
                }
                if ($.fn.bgStretcher.settings.slideDirection == 'SW' || $.fn.bgStretcher.settings.slideDirection == 'SE') {
                    $.fn.bgStretcher.settings.slideDirection = 'S';
                }
                if ($.fn.bgStretcher.settings.slideDirection == 'S' || $.fn.bgStretcher.settings.slideDirection == 'E') {
                    settings.sequenceMode = 'back';
                    $(allLIs).removeClass('bgs-current');
                    $(allLIs).eq($(allLIs).length - $.fn.bgStretcher.settings.startElementIndex - 1).addClass('bgs-current');
                    if ($.fn.bgStretcher.settings.slideDirection == 'E') {
                        l = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * $(containerStr).width() * (-1);
                        t = 0;
                    } else { // S
                        t = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * $(containerStr).height() * (-1);
                        l = 0;
                    }
                    $(containerStr + ' UL').css({left: l + 'px', top: t + 'px'});
                } else {
                    settings.sequenceMode = 'normal';
                    if ($.fn.bgStretcher.settings.startElementIndex != 0) {
                        if ($.fn.bgStretcher.settings.slideDirection == 'N') {
                            t = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * $(containerStr).height() * (-1);
                            l = 0;
                        } else { // W
                            l = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * $(containerStr).width() * (-1);
                            t = 0;
                            console.log(l);
                        }
                        $(containerStr + ' UL').css({left: l + 'px', top: t + 'px'});
                    }
                }
            }

            if ($(settings.buttonNext).length || $(settings.buttonPrev).length || $(settings.pagination).length) {
                if (settings.sequenceMode == 'random') {
                    if (typeof(console) !== 'undefined' && console != null) {
                        console.log('Don\'t use random mode width prev-button, next-button and pagination.');
                    }
                } else {
                    /*  Prev and Next Buttons init  */
                    if ($(settings.buttonPrev).length) {
                        $(settings.buttonPrev).addClass('bgStretcherNav bgStretcherNavPrev');
                        $(settings.buttonPrev).click(function () {
                            $.fn.bgStretcher.buttonSlide('prev');
                        });
                    }
                    if ($(settings.buttonNext).length) {
                        $(settings.buttonNext).addClass('bgStretcherNav bgStretcherNavNext');
                        $(settings.buttonNext).click(function () {
                            $.fn.bgStretcher.buttonSlide('next');
                        });
                    }
                    /*  Pagination  */
                    if ($(settings.pagination).length) {
                        $.fn.bgStretcher.pagination();
                    }
                }
            }

            /*  Random mode init  */
            if (settings.sequenceMode == 'random') {
                var i = Math.floor(Math.random() * $(allLIs).length);
                $.fn.bgStretcher.buildRandom(i);
                if (settings.transitionEffect != 'simpleSlide') {
                    $.fn.bgStretcher.settings.startElementIndex = i;
                }
                stratElement = i;
            } else {
                if ($.fn.bgStretcher.settings.startElementIndex > ($(allLIs).length - 1)) $.fn.bgStretcher.settings.startElementIndex = 0;
                stratElement = $.fn.bgStretcher.settings.startElementIndex;
                if (settings.transitionEffect == 'simpleSlide') {
                    if ($.fn.bgStretcher.settings.slideDirection == 'S' || $.fn.bgStretcher.settings.slideDirection == 'E') {
                        stratElement = $(allLIs).length - 1 - $.fn.bgStretcher.settings.startElementIndex;
                    }
                }
            }

            $(allLIs).eq(stratElement).show().addClass('bgs-current');
            $.fn.bgStretcher.loadImg($(allLIs).eq(stratElement));

            /*  Go slideshow  */
            if (settings.slideShow && $(allLIs).length > 1) {
                _bgStretcherTm = setTimeout('$.fn.bgStretcher.slideShow(\'' + $.fn.bgStretcher.settings.sequenceMode + '\', -1)', settings.nextSlideDelay);
            }

        };

        function _resize(body_content) {
            var winW = 0;
            var winH = 0;
            var contH = 0;
            var contW = 0;

            if ($('BODY').hasClass('bgStretcher-container')) {
                winW = $(window).width();
                winH = $(window).height();
                if (($.browser.msie) && (parseInt(jQuery.browser.version) == 6)) {
                    $(window).scroll(function () {
                        $('#' + settings.imageContainer).css('top', $(window).scrollTop());
                    });
                }
            } else {
                $('.bgstretcher').css('position', 'absolute').css('top', '0px');
                winW = body_content.width();
                winH = body_content.height();
            }

            var imgW = 0, imgH = 0;
            var leftSpace = 0;

            //	Max image size
            if (settings.maxWidth != 'auto') {
                if (winW > settings.maxWidth) {
                    leftSpace = (winW - settings.maxWidth) / 2;
                    contW = settings.maxWidth;
                } else contW = winW;
            } else contW = winW;
            if (settings.maxHeight != 'auto') {
                if (winH > settings.maxHeight) {
                    contH = settings.maxHeight;
                } else contH = winH;
            } else contH = winH;

            //	Update container's size
            container.width(contW);
            container.height(contH);

            //	Non-proportional resize
            if (!settings.resizeProportionally) {
                imgW = contH;
                imgH = contH;
            } else {
                var initW = settings.imageWidth, initH = settings.imageHeight;
                var ratio = initH / initW;

                imgW = contW;
                imgH = Math.round(contW * ratio);

                if (imgH < contH) {
                    imgH = contH;
                    imgW = Math.round(imgH / ratio);
                }
            }

            // Anchoring
            var mar_left = 0;
            var mar_top = 0;
            var anchor_arr;
            if ($.fn.bgStretcher.settings.anchoring != 'left top') {
                anchor_arr = ($.fn.bgStretcher.settings.anchoring).split(' ');
                if (anchor_arr[0] == 'right') {
                    mar_left = (winW - contW);
                } else {
                    if (anchor_arr[0] == 'center') mar_left = Math.round((winW - contW) / 2);
                }
                if (anchor_arr[1] == 'bottom') {
                    mar_top = (winH - contH);
                } else {
                    if (anchor_arr[1] == 'center') {
                        mar_top = Math.round((winH - contH) / 2);
                    }
                }
                container.css('marginLeft', mar_left + 'px').css('marginTop', mar_top + 'px');
            }
            mar_left = 0;
            mar_top = 0;
            if ($.fn.bgStretcher.settings.anchoringImg != 'left top') {
                anchor_arr = ($.fn.bgStretcher.settings.anchoringImg).split(' ');
                if (anchor_arr[0] == 'right') {
                    mar_left = (contW - imgW);
                } else {
                    if (anchor_arr[0] == 'center') mar_left = Math.round((contW - imgW) / 2);
                }
                if (anchor_arr[1] == 'bottom') {
                    mar_top = (contH - imgH);
                } else {
                    if (anchor_arr[1] == 'center') {
                        mar_top = Math.round((contH - imgH) / 2);
                    }
                }
            }
            img_options['mar_left'] = mar_left;
            img_options['mar_top'] = mar_top;

            //	Apply new size for images
            if (container.find('LI:first').hasClass('swf-mode')) {

                var path_swf = container.find('LI:first').html();
                container.find('LI:first').html('<div id="bgstretcher-flash">&nbsp;</div>');

                var header = new SWFObject('flash/stars.swf', 'flash-obj', contW, contH, '9');
                header.addParam('wmode', 'transparent');
                header.write('bgstretcher-flash');

            }
            ;
            img_options['imgW'] = imgW;
            img_options['imgH'] = imgH;

            if (!settings.resizeAnimate) {
                container.children('UL').children('LI.img-loaded').find('IMG').css({
                    'marginLeft': img_options["mar_left"] + 'px',
                    'marginTop': img_options["mar_top"] + 'px'
                });
                container.children('UL').children('LI.img-loaded').find('IMG').css({
                    'width': img_options["imgW"] + 'px',
                    'height': img_options["imgH"] + 'px'
                });
            } else {
                container.children('UL').children('LI.img-loaded').find('IMG').animate({
                    'marginLeft': img_options["mar_left"] + 'px',
                    'marginTop': img_options["mar_top"] + 'px'
                }, 'normal');
                container.children('UL').children('LI.img-loaded').find('IMG').animate({
                    'width': img_options["imgW"] + 'px',
                    'height': img_options["imgH"] + 'px'
                }, 'normal');
            }

            $(allLIs).width(container.width()).height(container.height());

            if ($.fn.bgStretcher.settings.transitionEffect == 'simpleSlide') {
                if ($.fn.bgStretcher.settings.slideDirection == 'W' || $.fn.bgStretcher.settings.slideDirection == 'E') {
                    container.children('UL').width(container.width() * $(allLIs).length).height(container.height());
                    if ($(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) != -1) {
                        l = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * container.width() * (-1);
                        container.children('UL').css({left: l + 'px'});
                    }
                } else {
                    container.children('UL').height(container.height() * $(allLIs).length).width(container.width());
                    if ($(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) != -1) {
                        t = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * $(containerStr).height() * (-1);
                        container.children('UL').css({top: t + 'px'});
                    }
                }
            }

        };

        function _genHtml(body_content) {
            var code = '';
            var cur_bgstretcher;

            body_content.each(function () {
                $(this).wrapInner('<div class="bgstretcher-page" />').wrapInner('<div class="bgstretcher-area" />');
                code = '<div id="' + settings.imageContainer + '" class="bgstretcher"><ul>';
                // if swf
                if (settings.images.length) {
                    var ext = settings.images[0].split('.');
                    ext = ext[ext.length - 1];

                    if (ext != 'swf') {
                        var ind = 0;
                        for (i = 0; i < settings.images.length; i++) {
                            if (settings.transitionEffect == 'simpleSlide' && settings.sequenceMode == 'back')
                                ind = settings.images.length - 1 - i;
                            else ind = i;
                            if ($.fn.bgStretcher.settings.preloadImg) {
                                code += '<li><span class="image-path">' + settings.images[ind] + '</span></li>';
                            } else {
                                code += '<li class="img-loaded"><img src="' + settings.images[ind] + '" alt="" /></li>';
                            }
                        }
                    } else {
                        code += '<li class="swf-mode">' + settings.images[0] + '</li>';
                    }
                }

                code += '</ul></div>';
                cur_bgstretcher = $(this).children('.bgstretcher-area');
                $(code).prependTo(cur_bgstretcher);
                cur_bgstretcher.css({position: 'relative'});
                cur_bgstretcher.children('.bgstretcher-page').css({'position': 'relative', 'z-index': 3,'top':0,'width':body_content.width(),'height':body_content.height()});
            });

        };

        /*  Start bgStretcher  */
        this.addClass('bgStretcher-container');
        _build(this);
    };

    $.fn.bgStretcher.loadImg = function (obj) {
        if (obj.hasClass('img-loaded')) return true;
        obj.find('SPAN.image-path').each(function () {
            var imgsrc = $(this).html();
            var imgalt = '';
            var parent = $(this).parent();
            var img = new Image();

            $(img).load(function () {
                $(this).hide();
                parent.prepend(this);
                $(this).fadeIn('100');
            }).error(function () {
            }).attr('src', imgsrc).attr('alt', imgalt);

            $(img).css({'marginLeft': img_options["mar_left"] + 'px', 'marginTop': img_options["mar_top"] + 'px'});
            $(img).css({'width': img_options["imgW"] + 'px', 'height': img_options["imgH"] + 'px'});
        });
        obj.addClass('img-loaded');
        return true;
    }

    $.fn.bgStretcher.play = function () {
        _bgStretcherPause = false;
        $.fn.bgStretcher._clearTimeout();
        $.fn.bgStretcher.slideShow($.fn.bgStretcher.settings.sequenceMode, -1);

    };

    $.fn.bgStretcher._clearTimeout = function () {
        if (_bgStretcherTm != null) {
            clearTimeout(_bgStretcherTm);
            _bgStretcherTm = null;
        }
    }

    $.fn.bgStretcher.pause = function () {
        _bgStretcherPause = true;
        $.fn.bgStretcher._clearTimeout();
    };

    $.fn.bgStretcher.sliderDestroy = function () {
        var cont = $('.bgstretcher-page').html();
        $('.bgStretcher-container').html('').html(cont).removeClass('bgStretcher-container');
        $.fn.bgStretcher._clearTimeout();
        _bgStretcherPause = false;
    }

    /*  Slideshow  */
    $.fn.bgStretcher.slideShow = function (sequence_mode, index_next) {
        _bgStretcherAction = true;
        if ($(allLIs).length < 2) return true;
        var current = $(containerStr + ' LI.bgs-current');
        var next;

        if (index_next == -1) {
            switch (sequence_mode) {
                case 'back':
                    next = current.prev();
                    if (!next.length) {
                        next = $(containerStr + ' LI:last');
                    }
                    break;
                case 'random':
                    if (r_image == $(containerStr + ' LI').length) {
                        $.fn.bgStretcher.buildRandom(random_line[$(containerStr + ' LI').length - 1]);
                        r_image = 0;
                    }
                    next = $(containerStr + ' LI').eq(random_line[r_image]);
                    r_image++;
                    break;
                default:
                    next = current.next();
                    if (!next.length) {
                        next = $(containerStr + ' LI:first');
                    }
            }
        } else {
            next = $(containerStr + ' LI').eq(index_next);
        }

        $(containerStr + ' LI').removeClass('bgs-current');
        $.fn.bgStretcher.loadImg(next);
        next.addClass('bgs-current');

        switch ($.fn.bgStretcher.settings.transitionEffect) {
            case 'fade':
                $.fn.bgStretcher.effectFade(current, next);
                break;
            case 'simpleSlide':
                $.fn.bgStretcher.simpleSlide();
                break;
            case 'superSlide':
                $.fn.bgStretcher.superSlide(current, next, sequence_mode);
                break;
            default :
                $.fn.bgStretcher.effectNone(current, next);

        }
        if ($($.fn.bgStretcher.settings.pagination).find('LI').length) {
            $($.fn.bgStretcher.settings.pagination).find('LI.showPage').removeClass('showPage');
            $($.fn.bgStretcher.settings.pagination).find('LI').eq($(containerStr + ' LI').index($(containerStr + ' LI.bgs-current'))).addClass('showPage');
        }

        // callback
        if ($.fn.bgStretcher.settings.callbackfunction) {
            if (typeof $.fn.bgStretcher.settings.callbackfunction == 'function')
                $.fn.bgStretcher.settings.callbackfunction.call();
        }

        if (!_bgStretcherPause) {
            _bgStretcherTm = setTimeout('$.fn.bgStretcher.slideShow(\'' + $.fn.bgStretcher.settings.sequenceMode + '\', -1)', $.fn.bgStretcher.settings.nextSlideDelay);
        }
    };

    /*  Others effects  */
    $.fn.bgStretcher.effectNone = function (current, next) {
        next.show();
        current.hide();
        _bgStretcherAction = false;
    };
    $.fn.bgStretcher.effectFade = function (current, next) {
        next.fadeIn($.fn.bgStretcher.settings.slideShowSpeed);
        current.fadeOut($.fn.bgStretcher.settings.slideShowSpeed, function () {
            _bgStretcherAction = false;
        });
    };

    $.fn.bgStretcher.simpleSlide = function () {
        var t, l;
        switch ($.fn.bgStretcher.settings.slideDirection) {
            case 'N':
            case 'S':
                t = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * $(containerStr).height() * (-1);
                l = 0;
                break;
            default:
                l = $(containerStr + ' LI').index($(containerStr + ' LI.bgs-current')) * $(containerStr).width() * (-1);
                t = 0;
        }
        $(containerStr + ' UL').animate({
            left: l + 'px',
            top: t + 'px'
        }, $.fn.bgStretcher.settings.slideShowSpeed, function () {
            _bgStretcherAction = false;
        });

    };

    $.fn.bgStretcher.superSlide = function (current, next, sequence_mode) {
        var t, l;
        switch ($.fn.bgStretcher.settings.slideDirection) {
            case 'S':
                t = $(containerStr).height();
                l = 0;
                break;
            case 'E':
                t = 0;
                l = $(containerStr).width();
                break;
            case 'W':
                t = 0;
                l = $(containerStr).width() * (-1);
                break;
            case 'NW':
                t = $(containerStr).height() * (-1);
                l = $(containerStr).width() * (-1);
                break;
            case 'NE':
                t = $(containerStr).height() * (-1);
                l = $(containerStr).width();
                break;
            case 'SW':
                t = $(containerStr).height();
                l = $(containerStr).width() * (-1);
                break;
            case 'SE':
                t = $(containerStr).height();
                l = $(containerStr).width();
                break;
            default:
                t = $(containerStr).height() * (-1);
                l = 0;

        }

        if (sequence_mode == 'back') {
            next.css({'z-index': 2, top: t + 'px', left: l + 'px'});
            next.show();
            next.animate({left: '0px', top: '0px'}, $.fn.bgStretcher.settings.slideShowSpeed, function () {
                current.hide();
                $(this).css({'z-index': 1});
                _bgStretcherAction = false;
            });
        } else {
            current.css('z-index', 2);
            next.show();
            current.animate({left: l + 'px', top: t + 'px'}, $.fn.bgStretcher.settings.slideShowSpeed, function () {
                $(this).hide().css({'z-index': 1, top: '0px', left: '0px'});
                _bgStretcherAction = false;
            });
        }
    };

    /*  Build line random images  */
    $.fn.bgStretcher.buildRandom = function (el_not) {
        var l = $(allLIs).length;
        var i, j, rt;
        for (i = 0; i < l; i++) {
            random_line[i] = i;
            random_temp[i] = Math.random() * l;
        }
        for (i = 0; i < l; i++) {
            for (j = 0; j < (l - i - 1); j++) {
                if (random_temp[j] > random_temp[j + 1]) {
                    rt = random_temp[j];
                    random_temp[j] = random_temp[j + 1];
                    random_temp[j + 1] = rt;
                    rt = random_line[j];
                    random_line[j] = random_line[j + 1];
                    random_line[j + 1] = rt;
                }
            }
        }

        if (random_line[0] == el_not) {
            rt = random_line[0];
            random_line[0] = random_line[l - 1];
            random_line[l - 1] = rt;
        }
    };

    /*  Prev and Next buttons */
    $.fn.bgStretcher.buttonSlide = function (button_point) {
        if (_bgStretcherAction || ($(allLIs).length < 2)) return false;
        var mode = '';
        if (button_point == 'prev') {
            mode = 'back';
            if ($.fn.bgStretcher.settings.sequenceMode == 'back') mode = 'normal';
        } else {
            mode = $.fn.bgStretcher.settings.sequenceMode;
        }
        $(allLIs).stop(true, true);
        $.fn.bgStretcher._clearTimeout();
        $.fn.bgStretcher.slideShow(mode, -1);
        return false;
    };

    /*  Pagination  */
    $.fn.bgStretcher.pagination = function () {
        var l = $(allLIs).length;
        var output = '';
        var i = 0;
        if (l > 0) {
            output += '<ul>';
            for (i = 0; i < l; i++) {
                output += '<li><a href="javascript:;">' + (i + 1) + '</a></li>';
            }
            output += '</ul>';
            $($.fn.bgStretcher.settings.pagination).html(output);
            $($.fn.bgStretcher.settings.pagination).find('LI:first').addClass('showPage');

            $($.fn.bgStretcher.settings.pagination).find('A').click(function () {
                if ($(this).parent().hasClass('showPage')) return false;
                $(allLIs).stop(true, true);
                $.fn.bgStretcher._clearTimeout();
                $.fn.bgStretcher.slideShow($.fn.bgStretcher.settings.sequenceMode, $($.fn.bgStretcher.settings.pagination).find('A').index($(this)));
                return false;
            });

        }
        return false;
    }

    /*  Default Settings  */
    $.fn.bgStretcher.defaults = {
        imageContainer: 'bgstretcher',
        resizeProportionally: true,
        resizeAnimate: false,
        images: [],
        imageWidth: 1024,
        imageHeight: 768,
        maxWidth: 'auto',
        maxHeight: 'auto',
        nextSlideDelay: 3000,
        slideShowSpeed: 'normal',
        slideShow: true,
        transitionEffect: 'fade', // none, fade, simpleSlide, superSlide
        slideDirection: 'N', // N, S, W, E, (if superSlide - NW, NE, SW, SE)
        sequenceMode: 'normal', // back, random
        buttonPrev: '',
        buttonNext: '',
        pagination: '',
        anchoring: 'left top', // right bottom center
        anchoringImg: 'left top', // right bottom center
        preloadImg: false,
        startElementIndex: 0,
        callbackfunction: null
    };
    $.fn.bgStretcher.settings = {};
})(jQuery);