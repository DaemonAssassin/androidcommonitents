package com.gmail.mateendev3.androidcommonintents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button btnSetAlarm, btnSetTimer, btnSetCalendar, btnShowAllAlarms, btnSetCalendarBro,
            btnOpenCamera, btnContactApp, btnSendEmail;
    TextView tvResult;
    public static final int IMAGE_CODE = 123;
    Uri mData;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        ivImage = findViewById(R.id.iv_image);
        btnSetAlarm = findViewById(R.id.btn_set_alarm);
        btnSetTimer = findViewById(R.id.btn_set_timer);
        btnSetCalendar = findViewById(R.id.btn_set_calendar);
        btnShowAllAlarms = findViewById(R.id.btn_show_all_alarms);
        btnSetCalendarBro = findViewById(R.id.btn_set_calendar_bro);
        btnOpenCamera = findViewById(R.id.btn_open_camera);
        btnContactApp = findViewById(R.id.btn_contact_app);
        btnSendEmail = findViewById(R.id.btn_send_email);

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Problems:
                //1. In Create an alarm (1 -> Extra Days not working in mobile but working in emulator
                //but monday is not setting, 2 -> How to set Ringtone?, 3 -> Vibrate is not working)
                Calendar calendar = Calendar.getInstance();
                ArrayList<Integer> days = new ArrayList<>();
                days.add(calendar.get(Calendar.SUNDAY));
                days.add(calendar.get(Calendar.TUESDAY));
                days.add(calendar.get(Calendar.MONDAY));
                days.add(calendar.get(Calendar.WEDNESDAY));
                days.add(calendar.get(Calendar.THURSDAY));
                days.add(calendar.get(Calendar.FRIDAY));
                days.add(calendar.get(Calendar.SATURDAY));
                days.add(calendar.get(Calendar.SUNDAY));

                Intent intent = new Intent()
                        .setAction(AlarmClock.ACTION_SET_ALARM)
                        .putExtra(AlarmClock.EXTRA_HOUR, 11)
                        .putExtra(AlarmClock.EXTRA_MINUTES, 17)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, "Pick Aazia")
                        .putExtra(AlarmClock.EXTRA_RINGTONE, "silent")
                        .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Toast.makeText(MainActivity.this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "NO Alarm Found", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent()
                        .setAction(AlarmClock.ACTION_SET_TIMER)
                        .putExtra(AlarmClock.EXTRA_LENGTH, 30)
                        .putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, "TimerBeast");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Toast.makeText(MainActivity.this, "Timer Set", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
        btnSetCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, "title")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "Lahore")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 5_000)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 15_000);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        btnSetCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                startActivity(intent);
            }
        });
        btnSetCalendarBro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent = new Intent();
                calendarIntent.setAction(Intent.ACTION_INSERT);
                calendarIntent.setDataAndType(CalendarContract.Events.CONTENT_URI, "vnd.android.cursor.dir/event");
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "Flutter");
                calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Hello to the future");
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Bahawalpur");
                calendarIntent.putExtra(Intent.EXTRA_EMAIL, "mateendev3@gmail.com, mateen.mehmood7886@gmail.com");
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                /*In dono ka koi faida nai mila
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 0);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 90_00_000);*/
                if (calendarIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(calendarIntent);
                }
            }
        });
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "Click Photo",
                        "Get Image From Gallery",
                        "Start a camera App in still Mode",
                        "Start a camera App in video Mode"
                };
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Choose")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (items[which].equals("Click Photo")) {
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent, IMAGE_CODE);
                                    }
                                } else if (items[which].equals("Get Image From Gallery")) {
                                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(pickPhoto, 500);
                                } else if (items[which].equals("Start a camera App in still Mode")) {
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    }
                                } else if (items[which].equals("Start a camera App in video Mode")) {
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    }
                                }
                            }
                        })
                        .create();
                dialog.show();

            }
        });
        btnContactApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = {
                        "Select a contact",
                        "Select specific contact data",
                        "View a contact",
                        "Edit an existing contact",
                        "Insert a contact"
                };

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Choose Item")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (items[which].equals("Select a contact")) {

                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent, 100);
                                    }

                                } else if (items[which].equals("Select specific contact data")) {
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent, 200);
                                    }
                                } else if (items[which].equals("View a contact")) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setData(mData);
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    }

                                } else if (items[which].equals("Edit an existing contact")) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_EDIT);
                                    intent.setData(mData);
                                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "gg@gmail.com");
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    }
                                } else if (items[which].equals("Insert a contact")) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_INSERT);
                                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                                    intent.putExtra(ContactsContract.Intents.Insert.NAME, "Papa");
                                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, "03455341605");
                                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "papag@gmail.com");
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    }

                                }

                            }
                        })
                        .create();

                dialog.show();
            }
        });
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] items = {
                        "Compose an email with optional attachments",
                        "Retrieve a specific type of file"
                };
                String[] emails = {"mateen.mehmood7886@gmail.com", "pkbeast786@gmail.com"};

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Select Email Type")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (items[which].equals("Compose an email with optional attachments")) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND);
                                    intent.setType("*/*");
                                    intent.putExtra(Intent.EXTRA_EMAIL, emails);
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Calling for Programming");
                                    intent.putExtra(Intent.EXTRA_CC, "mateen.mehmood786@gmail.com");
                                    intent.putExtra(Intent.EXTRA_BCC, "aazia@gmail.com");
                                    intent.putExtra(Intent.EXTRA_TEXT, "I want to become a programmer of pro type.");
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "No activity found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if (items[which].equals("Retrieve a specific type of file")) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent, 101);
                                    }
                                }

                            }
                        })
                        .create();
                dialog.show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getParcelableExtra("data");
            //Image View par set karna hai
            //ivCameraImage.setImageBitmap(image);
        } else if (requestCode == 100 && resultCode == RESULT_OK) {
            Toast.makeText(MainActivity.this, "Contact: " + data.getData(), Toast.LENGTH_SHORT).show();
            mData = data.getData();
        } else if (requestCode == 500 && resultCode == RESULT_OK) {
            if (data != null) {
                Toast.makeText(MainActivity.this, "Calling", Toast.LENGTH_SHORT).show();
                Bitmap image = (Bitmap) data.getParcelableExtra("data");
                ivImage.setImageBitmap(image);
                /*Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        ivImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        cursor.close();
                    }
                }*/
            }
        } else if (requestCode == 200 && resultCode == RESULT_OK) {

            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection =
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);

            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                Toast.makeText(MainActivity.this, "NumberIndex: " + numberIndex + "\nNumber: " + number, Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 101 && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            ivImage.setImageBitmap(thumbnail);
        }
    }
}