// Thousand formatter
function kFormatter(num) {
	return Math.abs(num) > 999 ? Math.sign(num)*((Math.abs(num)/1000).toFixed(1)) + 'k' : Math.sign(num)*Math.abs(num)
}

$(document).on('click', '.btn-vote', function () {
	voteBtn = $(this);
	score = $(this).closest(".reactions").find(".postScore").text();
	score = parseInt(score);

	$(this).addClass("active").delay(300).queue(function(next) {
		$(this).removeClass("active");
		next();
	});

	if ($(voteBtn).hasClass('btn-vote-upvoted') || $(voteBtn).hasClass('btn-vote-downvoted')) {
		act = '0';
		if ($(voteBtn).hasClass('btn-vote-upvoted')) {
			newScore = score - 1;
		}
		else { // downvoted
			newScore = score + 1;
		}
	}
	else if ($(voteBtn).hasClass('btn-vote-upvote')) {
		act = '1';
		newScore = score + 1;
		otherVoteBtn = $(".li-dislike").find(".btn-vote");
	}
	else {
		act = '-1';
		newScore = score - 1;
		otherVoteBtn = $(".li-like").find(".btn-vote");
	}

	var model = {
		postID: $(this).data("submission"),
		action: act,
	};

	$.ajax({
		type: "POST",
		url: '/vote',
		data: model,
		success: function () {

			// get the button type (upvote or downvote)
			if (act == '0' && newScore < score) {
				$(voteBtn).removeClass("btn-vote-upvoted");
				$(voteBtn).addClass("btn-vote-upvote");
			}
			else  if (act == '0' && newScore > score) {
				$(voteBtn).removeClass("btn-vote-downvoted");
				$(voteBtn).addClass("btn-vote-downvote");
			}
			else if (act == '1') {
				$(voteBtn).removeClass("btn-vote-upvote");
				$(voteBtn).addClass("btn-vote-upvoted");

				if ($(otherVoteBtn).hasClass("btn-vote-downvoted")) {
					$(otherVoteBtn).removeClass("btn-vote-downvoted");
					$(otherVoteBtn).addClass("btn-vote-downvote");
					newScore = newScore + 1;
				}
			}
			else if (act == '-1') { // else
				$(voteBtn).removeClass("btn-vote-downvote");
				$(voteBtn).addClass("btn-vote-downvoted");

				if ($(otherVoteBtn).hasClass("btn-vote-upvoted")) {
					$(otherVoteBtn).removeClass("btn-vote-upvoted");
					$(otherVoteBtn).addClass("btn-vote-upvote");
					newScore = newScore - 1;
				}
			}

			$(voteBtn).closest(".reactions").find(".postScore").text(newScore);
		},
		error: function (e) {
			alert("Unexpected bad request happened: " + e);
		}
	});
});

$(document).ready(function() {

	function scoreMinify() {
		$('.postScore').each(function (i, obj) {
			score = $(this).text();
			$(this).text(kFormatter(score));
		});
	}

	// scoreMinify(); // BROKES UPVOTE/DOWNVOTE SCORES

	var siteDatePicker = function() {
		if ( $('.datepicker').length > 0 ) {
			$(this).datepicker();
		}
	};
	siteDatePicker();

	// redirect to submission create when clicking on textbox
	$("#submit-post-textarea").click(function() {
		window.location.href = "/submission/create";
	});

	// Convert unix epoch time
	function getPostedTime() {
		$('.post-time-posted').each(function (i, obj) {
			unix_timestamp = $(this).text();
			var postDate = new Date(unix_timestamp * 1000);
			$(this).text(moment(postDate).fromNow());
		});
	}
	getPostedTime();

	var fixmeTop = $('#footerWidget').offset().top;       // get initial position of the element

	$(window).scroll(function() {                  // assign scroll event listener

		var currentScroll = $(window).scrollTop(); // get current position

		if (currentScroll >= fixmeTop) {           // apply position: fixed if you
			$('#footerWidget').css({                      // scroll to that element or below it
				position: 'fixed',
				top: '0',
				right: '0',
				marginTop: '50px',
			});
		} else {                                   // apply position: static
			$('#footerWidget').css({                      // if you scroll above it
				position: 'static',
				marginTop: '0'
			});
		}

	});

});