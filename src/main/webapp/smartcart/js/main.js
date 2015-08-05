$(document).ready(function () {
    $("html").queryLoader2({barColor: "#FFF", backgroundColor: "#88d3fc", percentage: !0, barHeight: 3, completeAnimation: "fade", minimumTime: 200}), $("body").fadeIn("slow"), $(".learn-more").click(function (e) {
        e.preventDefault(), $("html,body").animate({scrollTop: $(this.hash).offset().top}, 1e3)
    }), $("ul.nav-pills li a").click(function () {
        $("ul.nav-pills li.active").removeClass("active"), $(this).parent("li").addClass("active")
    }), $("#parallax-quote").parallax("50%", .8), $("#parallax-connect").parallax("50%", .8), $(".parallax-content").parallax("50%", .3),
        $(".flexslider").flexslider({animation: "home-slide", selector: ".slides > li", controlNav: !0, directionNav: !1, direction: "vertical"}),
        $('.flexslider2').flexslider({
            animation: "fade",
            slideshowSpeed: 2500,
            animationSpeed: 700,
            selector: ".slides > li",
            controlNav: !0,
            directionNav: !1,
            animationLoop: !0,
            pauseOnAction: !0
        });
        $(".gmap").each(function (e, a) {
        var t = $(a), o = "http://maps.google.com/maps?hl=en&ie=utf8&output=embed&sensor=false&iwd=1&mrt=loc&t=m&q=" + encodeURIComponent(t.attr("data-address"));
        o += "&z=" + t.attr("data-zoom"), o += "true" == t.attr("data-bubble") ? "&iwloc=addr" : "&iwloc=near", t.attr("src", o)
    }), $(window).load(function () {
        var e = $(".grid-wrapper");
        e.isotope({filter: "*", animationOptions: {duration: 750, easing: "linear", queue: !1}}), $(".grid-controls li a").click(function () {
            $(".grid-controls .current").removeClass("current"), $(this).addClass("current");
            var a = $(this).attr("data-filter");
            return e.isotope({filter: a, animationOptions: {duration: 750, easing: "linear", queue: !1}}), !1
        })
    }), $(".mix a").hover(function () {
        return $(this).find(".overlay").stop().slideDown(500), !1
    }, function () {
        return $(this).find(".overlay").stop().slideUp(500), !1
    }), $(".team-footer").mouseenter(function () {
        $(".follow", this).stop().animate({top: 70}, "fast"), $(".follow", this).next().fadeIn()
    }), $(".team-footer").mouseleave(function () {
        $(".follow", this).stop().animate({top: 0}, "fast"), $(".follow", this).next().fadeOut()
    }), $(".grid-wrapper").magnificPopup({delegate: "a", type: "image", gallery: {enabled: !0}}), $(".navbar").sticky({topSpacing: 0}), $("#main-menu").onePageNav({currentClass: "active", changeHash: !1, scrollThreshold: .5, scrollSpeed: 750, filter: "", easing: "swing"}),
        $.vegas("slideshow", {backgrounds: [
        {src: "img/shop-wp.jpg"}
    ], overlay: true})
        ("overlay", {src: "img/overlays/16.png"}),
        $("#vegas-next").click(function () {
        $.vegas("next")
    }), $("#vegas-prev").click(function () {
        $.vegas("previous")
    }), $("#contact-form").validate({rules: {name: {minlength: 2, required: !0}, email: {required: !0, email: !0}, message: {minlength: 2, required: !0}}, highlight: function (e) {
        $(e).closest(".control-group").removeClass("success").addClass("error")
    }, success: function (e) {
        e.text("OK!").addClass("valid").closest(".control-group").removeClass("error").addClass("success")
    }}), $(".counter").waypoint(function () {
        $(this).countTo()
    }, {triggerOnce: !0, offset: "bottom-in-view"})


    /*
     Countdown initializer
     */
    var now = new Date();
    var countTo = new Date("August 23, 2014 16:00:00");
    $('.timer').countdown(countTo, function(event) {
        $(this).find('.days').text(event.offset.totalDays);
        $(this).find('.hours').text(event.offset.hours);
        $(this).find('.minutes').text(event.offset.minutes);
        $(this).find('.seconds').text(event.offset.seconds);
    });


    var flipInProgress = false;
    $(".flip3D").mouseenter(function() {
        if (flipInProgress) return;
        flipInProgress = true;

        setTimeout( function () {
            $("#flip1").addClass('flipOn');
        }, 0);
        setTimeout( function () {
            $("#flip2").addClass('flipOn');
        }, 300);
        setTimeout( function () {
            $("#flip3").addClass('flipOn');
        }, 600);
        setTimeout( function () {
            $("#flip4").addClass('flipOn');
        }, 900);

        setTimeout(function () {
            // animation
            $("#flip1").removeClass('flipOn');
            $("#flip2").removeClass('flipOn');
            $("#flip3").removeClass('flipOn');
            $("#flip4").removeClass('flipOn');

            flipInProgress = false;
        }, 4500);
    });



    });