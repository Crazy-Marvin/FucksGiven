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

        body: Center(
          child: Text('Settings', style: TextStyle(fontSize: 60)
          ),
        ),

    );
  }
}
