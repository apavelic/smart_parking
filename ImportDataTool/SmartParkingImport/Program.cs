using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MongoDB.Bson;
using MongoDB.Driver;
using RestSharp;
using MongoDB.Bson.IO;

namespace SmartParkingImport
{
    class Program
    {

        private static readonly DateTime START_DATE = new DateTime(2017, 4, 1, 12, 0, 0);
        private static readonly DateTime END_DATE = new DateTime(2017, 7, 18, 12, 0, 0);

        private static readonly string LOCAL_HOST = "http://localhost:8080/Rest";
        private static readonly string REMOTE_HOST = "http://139.59.215.1";

        private static readonly int[] PARKING_IDS = new int[] { 1, 2, 3, 4 };
        private static readonly int MIN = 1;
        private static readonly int MAX = 500;

        private static Random random;

        static void Main(string[] args)
        {
            random = new Random(10000);

            InsertInitialData();
        }

        private static void InsertInitialData()
        {
            foreach (int id in PARKING_IDS)
            {
                DateTime startDate = START_DATE;

                while (startDate <= END_DATE)
                {
                    startDate = startDate.AddMinutes(random.Next(20));
                    StateVM model = new StateVM
                    {
                        Date = startDate.ToString("dd.MM.yyyy hh:mm"),
                        ParkingId = id,
                        State = "NOTFREE"
                    };

                    InsertDocument(model);
                    int minutes = random.Next(MIN, MAX);
                    startDate = startDate.AddMinutes(minutes);

                    model = new StateVM
                    {
                        Date = startDate.ToString("dd.MM.yyyy hh:mm"),
                        ParkingId = id,
                        State = "FREE"
                    };

                    InsertDocument(model);
                }
            }
        }

        private static void InsertDocument(StateVM model)
        {
            RestClient client = new RestClient(REMOTE_HOST);
            RestRequest request = new RestRequest("state/{model}", Method.PUT);

            request.AddParameter("model", Newtonsoft.Json.JsonConvert.SerializeObject(model), ParameterType.UrlSegment);

            IRestResponse response = client.Execute(request);
        }
    }
}
