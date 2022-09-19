import 'package:flutter/material.dart';
import 'package:fucksgiven/db/Notes_database.dart';
import 'package:hexcolor/hexcolor.dart';

import '../model/model.dart';

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  late List<Note> notes;
  bool isLoading = false;
  final myController = TextEditingController();

  @override
  void initState() {
    refreshNotes();
  }
  Future refreshNotes() async{
    print('loadinggg........');
    // setState(() { isLoading = true;});
    this.notes = await NotesDatabase.instance.readAllNotes();

    print("Notes check:${notes}");
    // setState(() {
    //   isLoading = false;
    // });
  }

  Future addNote(title) async{
    final note = Note(title: title, createdTime: DateTime.now());

    await NotesDatabase.instance.create(note);
  }

  createDialogue(){
  return showDialog<void>(
    context: context,
    barrierDismissible: false, // user must tap button!
    builder: (BuildContext context) {
      return AlertDialog(
        title: const Text('Add data:'),
        content: TextField(
          controller: myController,
        ),
        actions: <Widget>[
          TextButton(
            child: const Text('Add'),
            onPressed: () {
              addNote(myController.text.toString());
              refreshNotes();
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
  var data = ["Today","30th June","27 May","22 May" , "7 jan"];

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
                  child: Text("Lorem Ipsum is simply dummy text.",
                    style: TextStyle(
                    fontSize: 17,
                  ),
                  ),
                );
              },
              itemCount: notes.length,
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