using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SmartParkingWeb.Core.Models;
using RestSharp;
using Newtonsoft.Json;

namespace SmartParkingWeb.Core.Services
{
    public class ParkingService
    {
        public IEnumerable<ParkingViewModel> GetParking()
        {
            RestClient client = new RestClient("http://localhost:8080/Rest");
            RestRequest request = new RestRequest("parking", Method.GET);

            IRestResponse response = client.Execute(request);

            string jsonContent = response.Content;
            IEnumerable<ParkingViewModel> model = JsonConvert.DeserializeObject<IEnumerable<ParkingViewModel>>(jsonContent);

            return model;
        }

        public List<StateViewModel> GetParkingStateHistory(string from, string to)
        {
            RestClient client = new RestClient("http://localhost:8080/Rest");
            RestRequest request = new RestRequest(Method.GET);

            string action = "state";
            if (from != null && to != null)
            {
                action += "/{from}/{to}";
                request.AddParameter("from", from, ParameterType.UrlSegment);
                request.AddParameter("to", to, ParameterType.UrlSegment);
            }

            request.Resource = action;

            IRestResponse response = client.Execute(request);

            string jsonContent = response.Content;
            List<StateViewModel> model = JsonConvert.DeserializeObject<List<StateViewModel>>(jsonContent);

            return model;
        }

        public Dictionary<int, Dictionary<double, int>> PrepareChartData(string from, string to)
        {
            IEnumerable<StateViewModel> model = GetParkingStateHistory(from, to).OrderBy(x => x.Date);
            var usedDates = model.Select(x => x.Date.Date).Distinct();
            var usedParkings = model.Select(x => x.ParkingId).Distinct();

            Dictionary<int, Dictionary<double, int>> parkingStateDictionary = new Dictionary<int, Dictionary<double, int>>();

            foreach (var parkingId in usedParkings)
            {
                parkingStateDictionary.Add(parkingId, new Dictionary<double, int>());

                foreach (var date in usedDates)
                {
                    IEnumerable<StateViewModel> currentParkingStateByDate = model.Where(x => x.Date.Date == date && x.ParkingId == parkingId);

                    foreach (StateViewModel state in currentParkingStateByDate)
                    {
                        if (state.State == Enumerations.ParkingStateEnum.NOTFREE)
                        {
                            var stateFree = model.FirstOrDefault(x => x.Date >= state.Date && x.ParkingId == parkingId && x.State == Enumerations.ParkingStateEnum.FREE);

                            TimeSpan time = stateFree.Date - state.Date;
                            double milliseconds = date.ToUniversalTime().Subtract(new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc)).TotalMilliseconds;


                            if (parkingStateDictionary[parkingId].ContainsKey(milliseconds) == false)
                            {
                                parkingStateDictionary[parkingId].Add(milliseconds, 0);
                            }

                            int hours = Convert.ToInt32(time.TotalMinutes / 60);
                            int minutes = Convert.ToInt32(time.TotalMinutes % 60);

                            if (minutes > 15)
                            {
                                hours++;
                            }

                            parkingStateDictionary[parkingId][milliseconds] += hours;
                        }
                    }
                }
                if (parkingStateDictionary[parkingId].Values.Sum() == 0)
                {
                    parkingStateDictionary.Remove(parkingId);
                }
            }

            return parkingStateDictionary;
        }
    }
}
