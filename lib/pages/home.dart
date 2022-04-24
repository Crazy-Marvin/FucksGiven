import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) {
    return
      Scaffold(

        body: Center(
          child:ListView(

            padding: const EdgeInsets.all(8),
            children: <Widget>[
              Container(
                height: 50,
                color:HexColor("#29A331").withOpacity(0.5),
                child: Text('Today', style: TextStyle(color:HexColor("#29A331")),),
              ),
              Container(
                height: 50,

                child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children:[Text('Entry B')]
                ),
              ),
              Container(
                height: 50,
                color: Colors.amber[100],
                child: const Center(child: Text('30th June')),
              ),
            ],
          )
        ),
          floatingActionButton: new FloatingActionButton(
              elevation: 0.0,
              child: new Icon(Icons.add),
              backgroundColor: new HexColor("#29A331"),
              onPressed: (){

              }
          )
      );

  }
}