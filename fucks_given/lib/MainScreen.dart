import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:fucksgiven/pages/Settings.dart';
import 'package:fucksgiven/pages/graph.dart';
import 'package:fucksgiven/pages/home.dart';
import 'package:hexcolor/hexcolor.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({Key? key}) : super(key: key);

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int currentIndex =0;
  final screens = [
    Home(),
    Graph(),
  ];
  List<String> title =[
    'Fucks given',
    'Stats',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text(title[currentIndex].toString(),style: TextStyle(color: Colors. black,fontSize: 25)),
        elevation: 0,
        backgroundColor: Colors.white,
        actions: <Widget>[
          IconButton(
            icon:ImageIcon(
              AssetImage("images/Settings.png"),
              color:HexColor("#29A331"),
            ),
            onPressed: () {
              Navigator.push(context,
                MaterialPageRoute(builder: (context) =>  Settings()),
              );
            },
          )
        ],


      ),
      body:screens[currentIndex],

      bottomNavigationBar: BottomNavigationBar(
        onTap: (index) => setState(() {
          currentIndex=index;

        })  ,
        currentIndex: currentIndex,
        selectedItemColor:HexColor("#29A331"),
        showSelectedLabels: false,
        showUnselectedLabels: false,
        items: [
          BottomNavigationBarItem(
            icon: SvgPicture.asset(
              "images/homeicon.svg",
            ),
            label: 'Home',



          ),
          BottomNavigationBarItem(
              icon: SvgPicture.asset(
                "images/chart.svg",
              ),
              label: 'Chart'

          ),
        ],
      ),

    );
  }
}
