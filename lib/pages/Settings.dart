import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';

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
        body: Center(
          child: Text('Settings', style: TextStyle(fontSize: 60)
          ),
        ),

    );
  }
}
