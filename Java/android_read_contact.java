/* Intent call
* The following lines of codes will be used to call an that activity.
*
*/

final int RQS_PICKCONTACT = 1;
final Uri uriContact = ContactsContract.Contacts.CONTENT_URI;
Intent intentPickContact = new Intent(Intent.ACTION_PICK, uriContact);
startActivityForResult(intentPickContact, RQS_PICKCONTACT);

//No need to change the method. It will return one phone number, the first one.

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        String returnVal = "";
        if(resultCode == RESULT_OK){
            if(requestCode == RQS_PICKCONTACT){
                Uri returnUri = data.getData();
                Cursor cursor = getContentResolver().query(returnUri, null, null, null, null);

                if(cursor.moveToNext()){
                    int columnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String contactID = cursor.getString(columnIndex_ID);

                    int columnIndex_HASPHONENUMBER = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                    String stringHasPhoneNumber = cursor.getString(columnIndex_HASPHONENUMBER);

                    if(stringHasPhoneNumber.equalsIgnoreCase("1")){
                        Cursor cursorNum = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                                null,
                                null);

                        //Get the first phone number
                        if(cursorNum.moveToNext()){
                            int columnIndex_number = cursorNum.getColumnIndex
                                    (ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String stringNumber = cursorNum.getString(columnIndex_number);

                            boolean flag = readAndWrite(stringNumber);

                            if(!flag)
                            {
                                Toast.makeText(getApplicationContext(), "Did not fetch data!", Toast.LENGTH_LONG).show();
                            }


                        }

                    }else{
                        returnVal = "No number";
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No information!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
