import 'dart:io';

import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sqflite/sqflite.dart';

class Settings extends StatefulWidget {
  const Settings({Key? key}) : super(key: key);

  @override
  State<Settings> createState() => _SettingsState();
}

class _SettingsState extends State<Settings> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Settings', style: TextStyle(color: Colors.black), ),

          backgroundColor: Colors.white,
          iconTheme: IconThemeData(
            color: Colors.black, //change your color here
          ),
        ),
        body: Column(
          children: [
            Container(
              height: 40,
              color:  new HexColor("#29A331").withOpacity(0.1),
              child: Align(
                  alignment: Alignment.centerLeft,
                  child: Padding(
                    padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
                    child: Text('General', style: TextStyle(color: new HexColor("#29A331") ,
                      fontSize: 17,
                      fontWeight: FontWeight.bold,
                    ),
                    ),
                  )
              ),
            ),
            Container(
              height: 60,
              color:  new HexColor("#ffffff").withOpacity(0.1),
              child: Align(
                  alignment: Alignment.centerLeft,
                  child: Padding(
                    padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('Appearnece',style: TextStyle(fontSize: 20),),
                        Text('Light',style: TextStyle(color: Colors.grey),),

                      ],
                    ),
                  )
              ),
            ),
            Container(
              height: 40,
              color:  new HexColor("#29A331").withOpacity(0.1),
              child: Align(
                  alignment: Alignment.centerLeft,
                  child: Padding(
                    padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
                    child: Text('Data', style: TextStyle(color: new HexColor("#29A331") ,
                      fontSize: 17,
                      fontWeight: FontWeight.bold,
                    ),
                    ),
                  )
              ),
            ),
            Container(

              color:  new HexColor("#ffffff").withOpacity(0.1),
              child: Align(
                  alignment: Alignment.centerLeft,
                  child: Padding(
                    padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        GestureDetector(
                          onTap: () async {
                            String dbPath = await getDatabasesPath();
                            try{
                              String backupPath = '/storage/my_database.db';

                              await File("$dbPath/my_database.db").copy("$backupPath/my_database.db");
                              print('Congrats');
                            }
                                catch(e){
                              print(e);
                                }
                          },
                          child: ListTile(
                            leading: Icon(Icons.ac_unit),
                              title: Text('Backup',style: TextStyle(fontSize: 20),)),
                        ),
                       Divider(),
                        ListTile(
                            leading: Icon(Icons.ac_unit),
                            title: Text('Restore',style: TextStyle(fontSize: 20),)),

                      ],
                    ),
                  )
              ),
            ),
          ],
        ),

    );
  }
}
