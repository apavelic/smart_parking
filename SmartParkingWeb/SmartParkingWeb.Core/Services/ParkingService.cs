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

        public ChartViewModel PrepareChartData(string from, string to)
        {
            List<StateViewModel> model = GetParkingStateHistory(from, to);

            return null;

        }
    }
}
