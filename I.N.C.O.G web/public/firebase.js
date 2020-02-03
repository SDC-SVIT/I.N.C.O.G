var storage = firebase.storage();
var storageRef = storage.ref();

var i = 0;
storageRef.child('images/').listAll().then(function(result) {
	result.items.forEach(function(imageRef) {
		i++;
		displayImage(i, imageRef);
		if(i==3)
			i = 0;
	});
});

function displayImage(i, images) {
	images.getDownloadURL().then(function(url){
		var insert = '';
		// insert += '<div class="col-sm-12 col-md-4">';
		insert += '<img src = ' + url + ' class="coolUI">';
		// insert += '</div>';
		
		if(i==1) {
			$(".one").append(insert);
		}
		else if(i==2) {
			$(".two").append(insert);
		}
		else if(i==3) {
			$(".three").append(insert);
		}

		// $(".row").append(insert);
	});
}