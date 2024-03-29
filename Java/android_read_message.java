//you don't need to change anything in this method. It will return a StringBuilder.

    private StringBuilder getAssociateMessage(String getAddress)
    {
        final String SMS_URI_INBOX = "content://sms/inbox"; //I can use /sent for sent messages as well. This will read sms sent my that specific number.

        Uri uri = Uri.parse(SMS_URI_INBOX);
        String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };

        Cursor cursor = getContentResolver().query(uri, projection,
                null, null, "date desc");

        StringBuilder smsBuilder = new StringBuilder();//new
        boolean flag = true; //if there is no sms from that number

        try {
//            startManagingCursor(cursor);

            if (cursor.moveToFirst()) { // must check the result to prevent exception
//               NEW

                int index_Address = cursor.getColumnIndex("address");
                int index_Person = cursor.getColumnIndex("person");
                int index_Body = cursor.getColumnIndex("body");
                int index_Date = cursor.getColumnIndex("date");
                int index_Type = cursor.getColumnIndex("type");

                do {
                    String strAddress = cursor.getString(index_Address);

                    if(strAddress.equalsIgnoreCase(getAddress)) {

                        int intPerson = cursor.getInt(index_Person);
                        String strbody = cursor.getString(index_Body);
                        long longDate = cursor.getLong(index_Date);
                        int int_Type = cursor.getInt(index_Type);

                        smsBuilder.append("[ ");
                        smsBuilder.append(strAddress + ", ");
                        smsBuilder.append(strbody + ", ");
                        smsBuilder.append(longDate);
                        smsBuilder.append(" ]\n\n");
                        flag = true;
                    }
                    else
                    {
                        if(!flag)
                            flag = !flag;
                    }
                } while (cursor.moveToNext());

                if (!cursor.isClosed()) {
                    cursor.close();
                    cursor = null;
                }
            } else {
                smsBuilder.append("no result!");
            }
        }
        catch (SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }
        if(flag)
            return smsBuilder;
        else
            return smsBuilder.append("no data");
    }
