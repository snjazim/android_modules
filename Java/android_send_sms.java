String body = "Hello World";
SmsManager smsManager = SmsManager.getDefault();
smsManager.sendTextMessage("NUMBER WITH Country code", null, body, null, null);
