const ripple = $('.ripple');

ripple.on('mousedown', function() { $(this).css('background-color', '#e0e0e0'); });
ripple.on('mouseup', function() { $(this).css('background-color', ''); });
