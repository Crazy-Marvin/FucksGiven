import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  createDialogue(){
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Add data:'),
          content: TextField(
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Add'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
  @override
  Widget build(BuildContext context) {
    var data = ["Today","30th June","27 May","22 May"];

    return
      Scaffold(

          body:ListView.builder(
            itemBuilder: (context,index){

              return Column(
                children: [
                  Container(
                    height: 40,
                    color:  new HexColor("#29A331").withOpacity(0.1),
                    child: Align(
                        alignment: Alignment.centerLeft,
                        child: Padding(
                          padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
                          child: Text(data[index], style: TextStyle(color: new HexColor("#29A331") ,
                            fontSize: 17,
                            fontWeight: FontWeight.bold,

                          ),

                          ),
                        )
                    ),
                  ),
                  ListView.builder(itemBuilder: (context, index){
                    return  Padding(
                      padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
                      child: Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
                        style: TextStyle(
                          fontSize: 17,

                        ),

                      ),
                    );
                  },
                    itemCount: 6,
                    physics: ClampingScrollPhysics(),
                    shrinkWrap: true,
                  ),
                ],
              );
            },
            itemCount: 4,),
          floatingActionButton: new FloatingActionButton(
              elevation: 0.0,
              child: new Icon(Icons.add),
              backgroundColor: new HexColor("#29A331"),
              onPressed: (){
                createDialogue();
              }
          )
      );

  }
}