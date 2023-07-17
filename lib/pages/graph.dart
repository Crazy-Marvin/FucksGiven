import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:intl/intl.dart';
import 'package:syncfusion_flutter_charts/charts.dart';
import '../db/Notes_database.dart';

import 'Settings.dart';
List<int> weekdatesData= [0,0,0,0,0,0,0];
var average = 0.0;
class Graph extends StatefulWidget {
  const Graph({Key? key}) : super(key: key);

  @override
  State<Graph> createState() => _GraphState();
}

class _GraphState extends State<Graph> {
  late TooltipBehavior _tooltipBehavior;



  @override
  void initState(){
    _tooltipBehavior = TooltipBehavior(enable: true);
    super.initState();
    getDatesData();
  }

  List<String> getPreviousWeekDates() {
    List<String> previousWeekDates = [];

    // Get the current date
    DateTime currentDate = DateTime.now();

    // Calculate the starting date of the previous week (6 days ago from the current date)
    DateTime previousWeekStartDate = currentDate.subtract(Duration(days: 6));

    // Iterate through the previous week's dates and add them to the list
    for (int i = 0; i < 7; i++) {
      // Calculate each date by adding 'i' days to the previous week's starting date
      DateTime date = previousWeekStartDate.add(Duration(days: i));

      // Format the date in the desired pattern 'July 17, 2023' and add it to the list
      String formattedDate = DateFormat('MMMM d, y').format(date);
      previousWeekDates.add(formattedDate);
    }

    return previousWeekDates;
  }

  getDatesData() async {
    print('loadinggg........');

    List<String> previousWeekDates = getPreviousWeekDates();
    var avgCount=0;
    for (int i = 0; i < previousWeekDates.length; i++) {
      int noteCount = await NotesDatabase.instance.getDocumentCountByDate(previousWeekDates[i]);
      print("Count for ${previousWeekDates[i]}: $noteCount");
      weekdatesData[i] = noteCount;
      avgCount = avgCount+noteCount;
      print("fayes: "+noteCount.toString());
    }
    setState(() {
      weekdatesData;
      average = avgCount/7;

    });

  }
  @override
  Widget build(BuildContext context) {


    final List<ChartData> chartData = [
    ChartData('M', weekdatesData[0]),
    ChartData('TU', weekdatesData[1]),
    ChartData('W', weekdatesData[2]),
    ChartData('TH', weekdatesData[3]),
    ChartData('F', weekdatesData[4]),
    ChartData('ST', weekdatesData[5]),
    ChartData('SN', weekdatesData[6]),

    ];

    return
      SafeArea(
        child: Scaffold(
          body: Column(

            children:[
              Container(
                margin: EdgeInsets.all(20),
                child: SizedBox(
                  height: 50,
                  child: Row(
                    children: [

                      Expanded(
                        child: OutlinedButton(
                            onPressed: (){
                              Navigator.push(context,
                                MaterialPageRoute(builder: (context) =>  Settings()),
                              );

                            },
                            child: Center(child: Text('W'))

                        ),
                      ),
                      Expanded(
                        child: OutlinedButton(onPressed: (){

                        }, child: Center(child: Text('M'))),
                      ),
                      Expanded(
                        child: OutlinedButton(onPressed: (){

                        }, child: Center(child: Text('Y'))),
                      ),

                    ],
                  ),
                ),
              ),
              Container(
                child: Row(
                  children: [
                    SizedBox(width: 20,),
                    Text(double.parse(average.toStringAsFixed(1)).toString(),style: TextStyle(fontSize: 25,fontWeight: FontWeight. bold)),
                    SizedBox(width: 8,),
                    Text('fucks given on average',style: TextStyle(fontSize: 20)),
                  ],
                ),
              ),
              SizedBox(
                height:5,
              ),
              Container(
                child: Row(
                  children: [
                    SizedBox(width: 20,),
                    Text('5 - 12 September',style: TextStyle(fontSize: 17)),
                  ],
                ),
              ),
              SizedBox(
                height:5,
              ),
                  Container(
                      child: SfCartesianChart(
                          primaryXAxis: CategoryAxis(),
                          series: <CartesianSeries>[
                            LineSeries<ChartData, String>(
                                color:HexColor("#29A331"),
                                dataSource: chartData,
                                xValueMapper: (ChartData data, _) => data.x,
                                yValueMapper: (ChartData data, _) => data.y,
                                markerSettings: MarkerSettings(
                                    isVisible: true,

                                    shape: DataMarkerType.circle

                                )
                            )
                          ]
                      )
                  )





            ],
          ),

        ),
      );
  }
}
class ChartData {
  ChartData(this.x, this.y);
  final String x;
  final int? y;
}
